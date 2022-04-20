package br.com.digitalbooking.carros.service;

import br.com.digitalbooking.carros.dto.productDTOs.ProductResponseDTO;
import br.com.digitalbooking.carros.exceptions.EntityNotFoundException;
import br.com.digitalbooking.carros.exceptions.UnreportedEssentialFieldException;
import br.com.digitalbooking.carros.model.Category;
import br.com.digitalbooking.carros.model.City;
import br.com.digitalbooking.carros.model.Image;
import br.com.digitalbooking.carros.model.Product;
import br.com.digitalbooking.carros.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static br.com.digitalbooking.carros.TestData.*;

@SpringBootTest
public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private AttributeRepository attributeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        resetProductData();
    }

    // -------------------------------- COMEÇO DOS TESTES DO MÉTODO selectAll --------------------------------
    @Test
    public void whenSelectAllThenReturnAListOfProductResponseDTO() {
        Mockito.when(productRepository.findAll()).thenReturn(List.of(product1));

        List<ProductResponseDTO> response = productService.selectAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        productResponseDTOComparator(product1ResponseDTO, response.get(0));
        Mockito.verify(productRepository, Mockito.times(1)).findAll();
    }
    // --------------------------------- FIM DOS TESTES DO MÉTODO selectAll ----------------------------------



    // ---------------------------------- COMEÇO DOS TESTES DO MÉTODO select ----------------------------------
    @Test
    public void whenSelectReturnAProductResponseDTO() {
        Mockito.when(productRepository.findById(product1.getId())).thenReturn(Optional.of(product1));

        ProductResponseDTO response = productService.select(product1.getId());

        Assertions.assertNotNull(response);
        productResponseDTOComparator(product1ResponseDTO, response);
        Mockito.verify(productRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }


    @Test
    public void whenSelectThrowAnEntityNotFoundException() {
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Long id = 1L;

        try {
            ProductResponseDTO response = productService.select(id);
            Assertions.fail("A product with invalid id was returned");
        } catch(Exception ex) {
            Assertions.assertEquals(EntityNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Não há nenhum veículo com o id " + id, ex.getMessage());
        }
        Mockito.verify(productRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }
    // ------------------------------------ FIM DOS TESTES DO MÉTODO select -----------------------------------



    // ------------------------------- COMEÇO DOS TESTES DO MÉTODO selectByCity -------------------------------
    @Test
    public void whenSelectByCityReturnAListOfProductResponseDTO() {
        Mockito.when(cityRepository.findById(city1.getId())).thenReturn(Optional.of(city1));
        Mockito.when(productRepository.findByCity(city1)).thenReturn(List.of(product1));

        List<ProductResponseDTO> response = productService.selectByCity(city1.getId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        productResponseDTOComparator(product1ResponseDTO, response.get(0));
        Mockito.verify(cityRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(productRepository, Mockito.times(1)).findByCity(Mockito.any(City.class));
    }


    @Test
    public void whenSelectByCityThrowAnEntityNotFoundException() {
        Mockito.when(cityRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(productRepository.findByCity(Mockito.any(City.class))).thenReturn(List.of());

        Long id = 1L;

        try {
            List<ProductResponseDTO> response = productService.selectByCity(id);
            Assertions.fail("A list of products with an invalid id city was returned");
        } catch(Exception ex) {
            Assertions.assertEquals(EntityNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Não há nenhuma cidade com o id " + id, ex.getMessage());
        }
        Mockito.verify(cityRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(productRepository, Mockito.times(0)).findByCity(Mockito.any(City.class));
    }
    // --------------------------------- FIM DOS TESTES DO MÉTODO selectByCity --------------------------------



    // ------------------------------- COMEÇO DOS TESTES DO MÉTODO selectByCategoy -------------------------------
    @Test
    public void whenSelectByCategoryReturnAListOfProductResponseDTO() {
        Mockito.when(categoryRepository.findById(category1.getId())).thenReturn(Optional.of(category1));
        Mockito.when(productRepository.findByCategory(category1)).thenReturn(List.of(product1));

        List<ProductResponseDTO> response = productService.selectByCategory(category1.getId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        productResponseDTOComparator(product1ResponseDTO, response.get(0));
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(productRepository, Mockito.times(1)).findByCategory(Mockito.any(Category.class));
    }


    @Test
    public void whenSelectByCategoryThrowAnEntityNotFoundException() {
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(productRepository.findByCategory(Mockito.any(Category.class))).thenReturn(List.of());

        Long id = 1L;

        try {
            List<ProductResponseDTO> response = productService.selectByCategory(id);
            Assertions.fail("A list of products with an invalid id category was returned");
        } catch(Exception ex) {
            Assertions.assertEquals(EntityNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Não há nenhum registro de Category com o id " + id, ex.getMessage());
        }
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(productRepository, Mockito.times(0)).findByCategory(Mockito.any(Category.class));
    }
    // -------------------------------- FIM DOS TESTES DO MÉTODO selectByCategory --------------------------------



    // ---------------------------------- COMEÇO DOS TESTES DO MÉTODO create -------------------------------
    @Test
    public void whenCreateThenReturnAProductResponseDTO() {
        Mockito.when(categoryRepository.findById(category1.getId())).thenReturn(Optional.of(category1));
        Mockito.when(cityRepository.findById(city1.getId())).thenReturn(Optional.of(city1));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        Mockito.when(imageRepository.save(Mockito.any(Image.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        ProductResponseDTO response = productService.create(product1RequestDTO);

        Assertions.assertNotNull(response);
        productResponseDTOComparator(product1ResponseDTO.noId(), response);
        verifyTimesCreate(1, 1, 1, product1RequestDTO.getImages().size());
    }


    @Test
    public void whenCreateAProductWithoutModelThenThrowAnUnreportedEssentialFieldException() {
        Mockito.when(categoryRepository.findById(category1.getId())).thenReturn(Optional.of(category1));
        Mockito.when(cityRepository.findById(city1.getId())).thenReturn(Optional.of(city1));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        Mockito.when(imageRepository.save(Mockito.any(Image.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        product1RequestDTO.setModel(null);

        try {
            ProductResponseDTO response = productService.create(product1RequestDTO);
            Assertions.fail("A product without model was created.");
        } catch(Exception ex) {
            Assertions.assertEquals(UnreportedEssentialFieldException.class, ex.getClass());
            Assertions.assertEquals("Valor do campo model não informado", ex.getMessage());
        }
        verifyTimesCreate(0, 0, 0, 0);
    }


    @Test
    public void whenCreateAProductWithoutBrandThenThrowAnUnreportedEssentialFieldException() {
        Mockito.when(categoryRepository.findById(category1.getId())).thenReturn(Optional.of(category1));
        Mockito.when(cityRepository.findById(city1.getId())).thenReturn(Optional.of(city1));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        Mockito.when(imageRepository.save(Mockito.any(Image.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        product1RequestDTO.setBrand(null);

        try {
            ProductResponseDTO response = productService.create(product1RequestDTO);
            Assertions.fail("A product without brand was created.");
        } catch(Exception ex) {
            Assertions.assertEquals(UnreportedEssentialFieldException.class, ex.getClass());
            Assertions.assertEquals("Valor do campo brand não informado", ex.getMessage());
        }
        verifyTimesCreate(0, 0, 0, 0);
    }


    @Test
    public void whenCreateAProductWithoutCostThenThrowAnUnreportedEssentialFieldException() {
        Mockito.when(categoryRepository.findById(category1.getId())).thenReturn(Optional.of(category1));
        Mockito.when(cityRepository.findById(city1.getId())).thenReturn(Optional.of(city1));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        Mockito.when(imageRepository.save(Mockito.any(Image.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        product1RequestDTO.setCost(null);

        try {
            ProductResponseDTO response = productService.create(product1RequestDTO);
            Assertions.fail("A product without cost was created.");
        } catch(Exception ex) {
            Assertions.assertEquals(UnreportedEssentialFieldException.class, ex.getClass());
            Assertions.assertEquals("Valor do campo cost não informado", ex.getMessage());
        }
        verifyTimesCreate(0, 0, 0, 0);
    }


    @Test
    public void whenCreateAProductWithAnInvalidCategoryThenThrowAnEntityNotFoundException() {
        Long idCategory = 3L;

        Mockito.when(categoryRepository.findById(idCategory)).thenReturn(Optional.empty());
        Mockito.when(cityRepository.findById(city1.getId())).thenReturn(Optional.of(city1));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        Mockito.when(imageRepository.save(Mockito.any(Image.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        product1RequestDTO.setIdCategory(idCategory);

        try {
            ProductResponseDTO response = productService.create(product1RequestDTO);
            Assertions.fail("A product with an invalid category was created.");
        } catch(Exception ex) {
            Assertions.assertEquals(EntityNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Não há nenhum registro de Category com o id " + idCategory, ex.getMessage());
        }
        verifyTimesCreate(1, 0, 0, 0);
    }


    @Test
    public void whenCreateAProductWithAnInvalidCityThenThrowAnEntityNotFoundException() {
        Long idCity = 3L;

        Mockito.when(categoryRepository.findById(category1.getId())).thenReturn(Optional.of(category1));
        Mockito.when(cityRepository.findById(idCity)).thenReturn(Optional.empty());
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        Mockito.when(imageRepository.save(Mockito.any(Image.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        product1RequestDTO.setIdCity(idCity);

        try {
            ProductResponseDTO response = productService.create(product1RequestDTO);
            Assertions.fail("A product with an invalid city was created.");
        } catch(Exception ex) {
            Assertions.assertEquals(EntityNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Não há nenhuma cidade com o id " + idCity, ex.getMessage());
        }
        verifyTimesCreate(1, 1, 0, 0);
    }
    // ------------------------------------ FIM DOS TESTES DO MÉTODO create --------------------------------



    // ---------------------------------- COMEÇO DOS TESTES DO MÉTODO update -------------------------------
    @Test
    public void whenUpdateThenReturnAProductResponseDTO() {
        mocksUpdateMethods();

        product2ResponseDTO.setImages(product1ResponseDTO.getImages());

        ProductResponseDTO response = productService.update(product1.getId(), product2RequestDTO.noId());

        Assertions.assertNotNull(response);
        productResponseDTOComparator(product2ResponseDTO.noId(), response);
        verifyTimesUpdate(1, 1, 1, 1);
    }

    @Test
    public void whenUpdateAProductWithNullIdThenThrowsAnUnreportedEssentialFieldException() {
        mocksUpdateMethods();

        try {
            ProductResponseDTO response = productService.update(null, product2RequestDTO.noId());
            Assertions.fail("A product without id was updated");
        } catch(Exception ex) {
            Assertions.assertEquals(UnreportedEssentialFieldException.class, ex.getClass());
            Assertions.assertEquals("Valor do campo id não informado", ex.getMessage());
        }
        verifyTimesUpdate(0, 0, 0, 0);
    }

    @Test
    public void whenUpdateAProductWithInvalidIdThenThrowsAnEntityNotFoundException() {
        mocksUpdateMethods();
        Long id = 3L;

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.empty());

        try {
            ProductResponseDTO response = productService.update(id, product2RequestDTO.noId());
            Assertions.fail("A product with invalid id was updated");
        } catch(Exception ex) {
            Assertions.assertEquals(EntityNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Não há nenhum veículo no banco de dados com o id " + id, ex.getMessage());
        }
        verifyTimesUpdate(1, 0, 0, 0);
    }

    @Test
    public void whenUpdateAProductWithInvalidCityThenThrowsAnEntityNotFoundException() {
        Long idCity = 3L;
        product2RequestDTO.setIdCity(idCity);

        mocksUpdateMethods();
        Mockito.when(cityRepository.findById(idCity)).thenReturn(Optional.empty());

        try {
            ProductResponseDTO response = productService.update(product1.getId(), product2RequestDTO.noId());
            Assertions.fail("A product was updated with an invalid city");
        } catch(Exception ex) {
            Assertions.assertEquals(EntityNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Não há nenhuma cidade com o id " + idCity, ex.getMessage());
        }
        verifyTimesUpdate(1, 1, 0, 0);
    }

    @Test
    public void whenUpdateAProductWithInvalidCategoryThenThrowsAnEntityNotFoundException() {
        Long idCategory = 3L;
        product2RequestDTO.setIdCategory(idCategory);

        mocksUpdateMethods();
        Mockito.when(categoryRepository.findById(idCategory)).thenReturn(Optional.empty());

        try {
            ProductResponseDTO response = productService.update(product1.getId(), product2RequestDTO.noId());
            Assertions.fail("A product was updated with an invalid category");
        } catch(Exception ex) {
            Assertions.assertEquals(EntityNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Não há nenhum registro de Category com o id " + idCategory, ex.getMessage());
        }
        verifyTimesUpdate(1, 1, 1, 0);
    }

    // métodos auxiliares dos testes do update
    private void verifyTimesUpdate(Integer productFindById, Integer cityFindById, Integer categoryFindById, Integer productSave) {
        Mockito.verify(productRepository, Mockito.times(productFindById)).findById(Mockito.anyLong());
        Mockito.verify(cityRepository, Mockito.times(cityFindById)).findById(Mockito.anyLong());
        Mockito.verify(categoryRepository, Mockito.times(categoryFindById)).findById(Mockito.anyLong());
        Mockito.verify(productRepository, Mockito.times(productSave)).save(Mockito.any(Product.class));
    }

    private void mocksUpdateMethods() {
        Mockito.when(productRepository.findById(product1.getId())).thenReturn(Optional.of(product1));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        Mockito.when(cityRepository.findById(city2.getId())).thenReturn(Optional.of(city2));
        Mockito.when(categoryRepository.findById(category2.getId())).thenReturn(Optional.of(category2));
    }
    // ------------------------------------ FIM DOS TESTES DO MÉTODO update --------------------------------



    // ---------------------------------- COMEÇO DOS TESTES DO MÉTODO delete -------------------------------
    @Test
    public void whenDeleteThenDeleteAProduct() {
        Mockito.when(productRepository.findById(product1.getId())).thenReturn(Optional.of(product1));
        Mockito.doNothing().when(productRepository).deleteById(Mockito.anyLong());

        productService.delete(product1.getId());

        verifyTimesDelete(1, 1);
    }

    @Test
    public void whenDeleteWithNullIdThenThrowAnUnreportedEssentialFieldException() {
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.doNothing().when(productRepository).deleteById(Mockito.anyLong());

        try {
            productService.delete(null);
            Assertions.fail("A product was deleted by a null id");
        } catch(Exception ex) {
            Assertions.assertEquals(UnreportedEssentialFieldException.class, ex.getClass());
            Assertions.assertEquals("Id não informado", ex.getMessage());
        }
        verifyTimesDelete(0, 0);
    }

    @Test
    public void whenDeleteWithInvalidIdThenThrowAnEntityNotFoundException() {
        Long id = 3L;

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.doNothing().when(productRepository).deleteById(Mockito.anyLong());

        try {
            productService.delete(id);
            Assertions.fail("A product was deleted by an invalid id");
        } catch(Exception ex) {
            Assertions.assertEquals(EntityNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Não há nenhum veículo com o id " + id, ex.getMessage());
        }
        verifyTimesDelete(1, 0);
    }

    private void verifyTimesDelete(Integer findById, Integer deleteById) {
        Mockito.verify(productRepository, Mockito.times(findById)).findById(Mockito.anyLong());
        Mockito.verify(productRepository, Mockito.times(deleteById)).deleteById(Mockito.anyLong());
    }
    // ---------------------------------- COMEÇO DOS TESTES DO MÉTODO delete -------------------------------



    private void productResponseDTOComparator(ProductResponseDTO p1, ProductResponseDTO p2) {
        Assertions.assertEquals(p1.getId(), p2.getId());
        Assertions.assertEquals(p1.getModel(), p2.getModel());
        Assertions.assertEquals(p1.getBrand(), p2.getBrand());
        Assertions.assertEquals(p1.getDescription(), p2.getDescription());
        Assertions.assertEquals(p1.getCost(), p2.getCost());

        Assertions.assertEquals(p1.getCategory().getId(), p2.getCategory().getId());
        Assertions.assertEquals(p1.getCategory().getTitle(), p2.getCategory().getTitle());
        Assertions.assertEquals(p1.getCategory().getDescription(), p2.getCategory().getDescription());
        Assertions.assertEquals(p1.getCategory().getUrlImage(), p2.getCategory().getUrlImage());

        Assertions.assertEquals(p1.getCity().getId(), p2.getCity().getId());
        Assertions.assertEquals(p1.getCity().getName(), p2.getCity().getName());
        Assertions.assertEquals(p1.getCity().getAddress(), p2.getCity().getAddress());

        Assertions.assertEquals(p1.getAttribute().getPower(), p2.getAttribute().getPower());
        Assertions.assertEquals(p1.getAttribute().getSeats(), p2.getAttribute().getSeats());
        Assertions.assertEquals(p1.getAttribute().getAcceleration(), p2.getAttribute().getAcceleration());
        Assertions.assertEquals(p1.getAttribute().getTransmission(), p2.getAttribute().getTransmission());
        Assertions.assertEquals(p1.getAttribute().getGas(), p2.getAttribute().getGas());
        Assertions.assertEquals(p1.getAttribute().getTcs(), p2.getAttribute().getTcs());
        Assertions.assertEquals(p1.getAttribute().getDoors(), p2.getAttribute().getDoors());

        Assertions.assertEquals(p1.getImages().size(), p2.getImages().size());
        for(int i = 0; i < p1.getImages().size(); i++) {
            Assertions.assertEquals(p1.getImages().get(i).getTitle(), p2.getImages().get(i).getTitle());
            Assertions.assertEquals(p1.getImages().get(i).getUrlImage(), p2.getImages().get(i).getUrlImage());
        }
    }

    private void verifyTimesCreate(Integer categoryFindById, Integer cityFindById, Integer productSave, Integer imageSave) {
        Mockito.verify(categoryRepository, Mockito.times(categoryFindById)).findById(Mockito.anyLong());
        Mockito.verify(cityRepository, Mockito.times(cityFindById)).findById(Mockito.anyLong());
        Mockito.verify(productRepository, Mockito.times(productSave)).save(Mockito.any(Product.class));
        Mockito.verify(imageRepository, Mockito.times(imageSave)).save(Mockito.any(Image.class));
    }
}