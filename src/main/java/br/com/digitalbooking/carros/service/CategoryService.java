package br.com.digitalbooking.carros.service;


import br.com.digitalbooking.carros.dto.CategoryDTO;
import br.com.digitalbooking.carros.exceptions.EntityNotFoundException;
import br.com.digitalbooking.carros.exceptions.UniqueAttributeValueRepeatedException;
import br.com.digitalbooking.carros.exceptions.UnreportedEssentialFieldException;
import br.com.digitalbooking.carros.model.Category;
import br.com.digitalbooking.carros.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> selectAll() {
        // transforma a lista de objetos do tipo Categoria em uma lista de DTOs e a retorna
        return categoryRepository.findAll().stream().map(category -> {
            return new CategoryDTO(category);
        }).collect(Collectors.toList());
    }

    public CategoryDTO select(Long id) {
        // se houver um registro no DB com o id informado, ele será retornado como um dto
        // caso contrário, uma exceção é lançada
        return new CategoryDTO(categoryRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException("Não há nenhum registro de Category com o id " + id);
        }));
    }

    public CategoryDTO create(CategoryDTO categoryDTO) {
        // caso o título (atributo essencial) seja nulo, lança uma exceção
        if (categoryDTO.getTitle() == null) throw new UnreportedEssentialFieldException("Título não informado");
        // caso já exista uma categoria com o mesmo título, lança uma exceção
        if (categoryRepository.findByTitleIs(categoryDTO.getTitle()) != null) throw new UniqueAttributeValueRepeatedException("Título já existe");
        // instancia uma nova categoria, salva no DB e retorna como um dto
        Category category = new Category(categoryDTO.noId());
        return new CategoryDTO(categoryRepository.save(category)).noId();
    }


    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        // tenta pegar um registro do DB pelo id. Caso não haja, lança uma exceção
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException("Não há nenhum registro com o id " + id);
        });

        // modifica apenas os campos não nulos passados na requisição
        if(categoryDTO.getTitle() != null) category.setTitle(categoryDTO.getTitle());
        if(categoryDTO.getDescription() != null) category.setDescription(categoryDTO.getDescription());
        if(categoryDTO.getUrlImage() != null) category.setUrlImage(categoryDTO.getUrlImage());

        // altera o registro no DB e retorna sua nova forma como um dto
        return new CategoryDTO(categoryRepository.save(category)).noId();
    }

    public void delete(Long id) {
        // verificando se há algum elemento no banco de dados com o id informado
        this.select(id);

        // excluindo o registro do banco de dados
        categoryRepository.deleteById(id);
    }
}