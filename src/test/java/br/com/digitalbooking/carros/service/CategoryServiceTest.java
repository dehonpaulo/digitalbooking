package br.com.digitalbooking.carros.service;

import br.com.digitalbooking.carros.dto.CategoryDTO;
import br.com.digitalbooking.carros.exceptions.EntityNotFoundException;
import br.com.digitalbooking.carros.exceptions.UniqueAttributeValueRepeatedException;
import br.com.digitalbooking.carros.exceptions.UnreportedEssentialFieldException;
import br.com.digitalbooking.carros.model.Category;
import br.com.digitalbooking.carros.repository.CategoryRepository;
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
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        resetCategoryData();
    }

    @Test
    public void whenSelectAllThenReturnAListOfCategoryDTO() {
        Mockito.when(categoryRepository.findAll()).thenReturn(List.of(category1));

        List<CategoryDTO> response = categoryService.selectAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(category1DTO.getId(), response.get(0).getId());
        Assertions.assertEquals(category1DTO.getTitle(), response.get(0).getTitle());
        Assertions.assertEquals(category1DTO.getDescription(), response.get(0).getDescription());
        Assertions.assertEquals(category1DTO.getUrlImage(), response.get(0).getUrlImage());
        Mockito.verify(categoryRepository, Mockito.times(1)).findAll();
    }



    @Test
    public void whenSelectReturnACategoryInstance() {
        Mockito.when(categoryRepository.findById(category1.getId())).thenReturn(Optional.of(category1));

        CategoryDTO response = categoryService.select(category1.getId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(category1DTO.getId(), response.getId());
        Assertions.assertEquals(category1DTO.getTitle(), response.getTitle());
        Assertions.assertEquals(category1DTO.getDescription(), response.getDescription());
        Assertions.assertEquals(category1DTO.getUrlImage(), response.getUrlImage());
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    public void whenSelectThrowAnEntityNotFoundException() {
        Long id = 1L;

        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        try {
            categoryService.select(id);
            Assertions.fail("A category with invalid id was returned");
        } catch(Exception ex) {
            Assertions.assertEquals(ex.getClass(), EntityNotFoundException.class);
            Assertions.assertEquals(ex.getMessage(), "Não há nenhum registro de Category com o id " + id);
        }
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }



    @Test
    public void whenCreateThenReturnACategoryDTO() {
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        Mockito.when(categoryRepository.findByTitleIs(Mockito.anyString())).thenReturn(null);

        CategoryDTO response = categoryService.create(category1DTO.noId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(category1DTO.getTitle(), response.getTitle());
        Assertions.assertEquals(category1DTO.getDescription(), response.getDescription());
        Assertions.assertEquals(category1DTO.getUrlImage(), response.getUrlImage());
        Mockito.verify(categoryRepository, Mockito.times(1)).findByTitleIs(Mockito.anyString());
        Mockito.verify(categoryRepository, Mockito.times(1)).save(Mockito.any(Category.class));
    }

    @Test
    public void whenCreateThenThrowAnUnreportedEssentialFieldException() {
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        Mockito.when(categoryRepository.findByTitleIs(Mockito.anyString())).thenReturn(null);

        category1DTO.setTitle(null);

        try {
            CategoryDTO response = categoryService.create(category1DTO.noId());
            Assertions.fail("A Category instance without a title was created");
        } catch(Exception ex) {
            Assertions.assertEquals(UnreportedEssentialFieldException.class, ex.getClass());
            Assertions.assertEquals("Título não informado", ex.getMessage());
        }
        Mockito.verify(categoryRepository, Mockito.times(0)).save(Mockito.any(Category.class));
        Mockito.verify(categoryRepository, Mockito.times(0)).findByTitleIs(Mockito.anyString());
    }

    @Test
    public void whenCreateThenThrowAnUniqueAttributeValueRepeatedException() {
        category2DTO.setTitle(category1DTO.getTitle());

        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        Mockito.when(categoryRepository.findByTitleIs(category1.getTitle())).thenReturn(category1);

        try {
            CategoryDTO response = categoryService.create(category2DTO.noId());
            Assertions.fail("A Category instance with repeated title was created");
        } catch(Exception ex) {
            Assertions.assertEquals(UniqueAttributeValueRepeatedException.class, ex.getClass());
            Assertions.assertEquals("Título já existe", ex.getMessage());
        }
        Mockito.verify(categoryRepository, Mockito.times(1)).findByTitleIs(Mockito.anyString());
        Mockito.verify(categoryRepository, Mockito.times(0)).save(Mockito.any(Category.class));
    }



    @Test
    public void whenUpdateThenReturnACategoryByIdDTOWithAllAttributesUpdated() {
        Mockito.when(categoryRepository.findById(category1.getId())).thenReturn(Optional.of(category1));
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        CategoryDTO response = categoryService.update(category1.getId(), category2DTO.noId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(category2DTO.getTitle(), response.getTitle());
        Assertions.assertEquals(category2DTO.getDescription(), response.getDescription());
        Assertions.assertEquals(category2DTO.getUrlImage(), response.getUrlImage());
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(categoryRepository, Mockito.times(1)).save(Mockito.any(Category.class));
    }

    @Test
    public void whenUpdateThenThrowAnEntityNotFoundException() {
        Long id = 1L;

        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        try {
            CategoryDTO response = categoryService.update(id, category2DTO.noId());
            Assertions.fail("A category instance with invalid id has been updated");
        } catch(Exception ex) {
            Assertions.assertEquals(EntityNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Não há nenhum registro com o id " + id, ex.getMessage());
        }
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(categoryRepository, Mockito.times(0)).save(Mockito.any(Category.class));
    }



    @Test
    public void whenDeleteThenTheCategoryIsDeleted() {
        Mockito.doNothing().when(categoryRepository).deleteById(Mockito.anyLong());
        Mockito.when(categoryRepository.findById(category1.getId())).thenReturn(Optional.of(category1));

        categoryService.delete(category1.getId());

        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    public void whenDeleteThrowAnEntityNotFoundException() {
        Long id = 1L;

        Mockito.doNothing().when(categoryRepository).deleteById(Mockito.anyLong());
        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        try {
            categoryService.delete(id);
        } catch(Exception ex) {
            Assertions.assertEquals(EntityNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Não há nenhum registro de Category com o id " + id, ex.getMessage());
        }
        Mockito.verify(categoryRepository, Mockito.times(0)).deleteById(Mockito.anyLong());
    }
}