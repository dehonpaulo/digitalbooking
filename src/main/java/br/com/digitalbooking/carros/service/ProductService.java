package br.com.digitalbooking.carros.service;

import br.com.digitalbooking.carros.dto.ImageDTO;
import br.com.digitalbooking.carros.dto.productDTOs.ProductRequestDTO;
import br.com.digitalbooking.carros.dto.productDTOs.ProductResponseDTO;
import br.com.digitalbooking.carros.exceptions.EntityNotFoundException;
import br.com.digitalbooking.carros.exceptions.UnreportedEssentialFieldException;
import br.com.digitalbooking.carros.model.*;
import br.com.digitalbooking.carros.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AttributeRepository attributeRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private BookingService bookingService;

    public List<ProductResponseDTO> selectAll() {
        return productRepository.findAll().stream().map(product -> {
            return new ProductResponseDTO(product);
        }).collect(Collectors.toList());
    }

    public ProductResponseDTO select(Long id) {
        return new ProductResponseDTO(productRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException("Não há nenhum veículo com o id " + id);
        }));
    }

    public List<ProductResponseDTO> selectByCity(Long idCity) {
        // tenta encontrar uma cidade no DB com o id informado. Caso não haja, lança uma exceção
        City city = cityRepository.findById(idCity).orElseThrow(() -> {
            return new EntityNotFoundException("Não há nenhuma cidade com o id " + idCity);
        });

        // retorna uma lista com todos os produtos da cidade informada como DTOs
        return productRepository.findByCity(city).stream().map(product -> {
            return new ProductResponseDTO(product);
        }).collect(Collectors.toList());
    }

    public List<ProductResponseDTO> findAvailableProductsByDatesAndCities(LocalDate checkIn, LocalDate checkOut, Long idCity) throws Exception{
        // verifica se não foi enviado uma data de checkout anterior a data de checkin, se sim ele manda uma exception
        if(checkOut.isBefore(checkIn)) {
            throw new Exception("A data de checkOut não pode ser anterior a data de checkIn");
        }

        // busca os produtos da cidade informada
        List<ProductResponseDTO> products = this.selectByCity(idCity);
        // procura os produtos daquela cidade que estão reservados naquelas datas
        List<Product> reservedProducts =
                productRepository.findReservedProductsByDatesAndCities(checkIn,
                        checkOut, idCity);

        // remove da lista de products os produtos que retornaram na busca de reservados
        products.removeIf(reservedProducts::contains);

        // cria a lista com os produtos que restaram
        List<ProductResponseDTO> productsDTO = new ArrayList<>();
        for (ProductResponseDTO product : products) {
            productsDTO.add(product);
        }
        return productsDTO;
    }

    public List<ProductResponseDTO> selectByCategory(Long idCategory) {
        // tenta encontrar uma categoria no DB com o id informado. Caso não haja, lança uma exceção
        Category category = categoryRepository.findById(idCategory).orElseThrow(() -> {
            return new EntityNotFoundException("Não há nenhum registro de Category com o id " + idCategory);
        });

        // retorna uma lista com todos os produtos da categoria informada como DTOs
        return productRepository.findByCategory(category).stream().map(product -> {
            return new ProductResponseDTO(product);
        }).collect(Collectors.toList());
    }

    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
        // instancia um novo produto
        Product product = new Product();

        // se o model (atributo essencial) não for nulo adiciona ao novo produto, senão lança uma exceção
        if (productRequestDTO.getModel() == null)
            throw new UnreportedEssentialFieldException("Valor do campo model não informado");
        product.setModel(productRequestDTO.getModel());

        // se o brand (atributo essencial) não for nulo adiciona ao novo produto, senão lança uma exceção
        if (productRequestDTO.getBrand() == null)
            throw new UnreportedEssentialFieldException("Valor do campo brand não informado");
        product.setBrand(productRequestDTO.getBrand());

        if (productRequestDTO.getCost() == null)
            throw new UnreportedEssentialFieldException("Valor do campo cost não informado");
        product.setCost(productRequestDTO.getCost());

        // adiciona o description ao produto
        product.setDescription(productRequestDTO.getDescription());

        // se for informado o id do Category, tenta encontrá-la no banco de dados
        if (productRequestDTO.getIdCategory() != null) {
            // se existir uma instância de Category com o id informado adiciona ao novo produto, senão lança uma exceção
            product.setCategory(categoryRepository.findById(productRequestDTO.getIdCategory()).orElseThrow(() -> {
                return new EntityNotFoundException("Não há nenhum registro de Category com o id " + productRequestDTO.getIdCategory());
            }));
        }

        // se for informado o id da cidade, tenta encontrá-la no banco de dados
        if (productRequestDTO.getIdCity() != null) {
            // se existir uma cidade com o id informado adiciona ao novo produto, senão lança uma exceção
            product.setCity(cityRepository.findById(productRequestDTO.getIdCity()).orElseThrow(() -> {
                return new EntityNotFoundException("Não há nenhuma cidade com o id " + productRequestDTO.getIdCity());
            }));
        }

        // se o attribute não for nulo, adiciona ao novo produto
        if (productRequestDTO.getAttribute() != null)
            product.setAttribute(new Attribute(productRequestDTO.getAttribute()));

        // salva o novo produto no banco de dados e converte o produto salvo em DTO
        Product productSaved = productRepository.save(product);
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(productSaved);

        // verifica se o images não é nulo
        if (productRequestDTO.getImages() != null) {
            // converte os imageDTO em images para salvar no banco de dados
            List<Image> images = productRequestDTO.getImages().stream().map(imageDTO -> {
                return new Image(null, imageDTO.getTitle(), imageDTO.getUrlImage(), productSaved);
            }).collect(Collectors.toList());

            // instancia uma lista de imagens para guardar os retornos da operação no banco de dados
            List<ImageDTO> imagesDTO = new ArrayList<>();

            // salva todas as imagens no banco de dados, converte novamente elas para DTO e guarda na lista
            for (Image image : images) {
                imagesDTO.add(new ImageDTO(imageRepository.save(image)));
            }

            // insere a lista de imageDTO no DTO de resposta
            productResponseDTO.setImages(imagesDTO);
        }

        // retorna o resultado da operação
        return productResponseDTO.noId();
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {
        // caso o id informado seja nulo, lança uma exceção
        if (id == null)
            throw new UnreportedEssentialFieldException("Valor do campo id não informado");

        // tenta encontrar um registro no DB com o id informado, caso não haja, lança uma exceção
        Product product = productRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException("Não há nenhum veículo no banco de dados com o id " + id);
        });

        // modifica apenas os campos não nulos passados na requisição
        if (productRequestDTO.getModel() != null) product.setModel(productRequestDTO.getModel());
        if (productRequestDTO.getBrand() != null) product.setBrand(productRequestDTO.getBrand());
        if (productRequestDTO.getDescription() != null) product.setDescription(productRequestDTO.getDescription());
        if (productRequestDTO.getCost() != null) product.setCost(productRequestDTO.getCost());
        if (productRequestDTO.getIdCity() != null) {
            // tenta encontrar uma cidade no banco de dados com o id informado. Caso não haja, lança uma exceção
            product.setCity(cityRepository.findById(productRequestDTO.getIdCity()).orElseThrow(() -> {
                return new EntityNotFoundException("Não há nenhuma cidade com o id " + productRequestDTO.getIdCity());
            }));
        }
        if (productRequestDTO.getIdCategory() != null) {
            // tenta encontrar uma categoria no banco de dados com o id informado. Caso não haja, lança uma exceção
            product.setCategory(categoryRepository.findById(productRequestDTO.getIdCategory()).orElseThrow(() -> {
                return new EntityNotFoundException("Não há nenhum registro de Category com o id " + productRequestDTO.getIdCategory());
            }));
        }
        if (productRequestDTO.getAttribute() != null) {
            // é criado um novo attribute para o product, caso não haja. Se houver, o attribute é apenas alterado
            if(product.getAttribute() == null) {
                product.setAttribute(new Attribute(productRequestDTO.getAttribute()));
            } else {
                Attribute attribute = new Attribute(productRequestDTO.getAttribute());
                attribute.setId(product.getAttribute().getId());
                product.setAttribute(attribute);
            }
        }

        // altera o produto no banco de dados e retorna sua nova forma como um dto
        return new ProductResponseDTO(productRepository.save(product)).noId();
    }

    public void delete(Long id) {
        // verifica se o id informado é nulo. Caso seja, lança uma exceção
        if(id == null) throw new UnreportedEssentialFieldException("Id não informado");

        // verifica se há algum produto no banco de dados com o id informado. Caso não haja, lança uma exceção
        this.select(id);

        // exclui o produto do banco de dados
        productRepository.deleteById(id);
    }
}


