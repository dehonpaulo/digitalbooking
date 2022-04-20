package br.com.digitalbooking.carros.controller;

import br.com.digitalbooking.carros.exceptions.UniqueAttributeValueRepeatedException;
import br.com.digitalbooking.carros.exceptions.UnreportedEssentialFieldException;
import br.com.digitalbooking.carros.exceptions.EntityNotFoundException;
import br.com.digitalbooking.carros.model.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryControllerIntegrationTest {
    private Category sedanCategory;
    private Category picapeCategory;
    private Category suvCategoryWithoutId;
    private Category esportivoCategoryWithoutId;
    private Category categoryWithoutIdAndTitle;

    private String sedanJson;
    private String picapeJson;
    private String suvJsonWithoutId;
    private String esportivoJsonWithoutId;
    private String jsonWithoutIdAndTitle;

    @Autowired
    private CategoryController categoryController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeAll
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
        this.objectMapper = new ObjectMapper();
        startUsefulObjects();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(suvJsonWithoutId));
    }



    @Test
    @Order(1)
    public void getAll_returnStatusCode200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is(this.suvCategoryWithoutId.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description", Matchers.is(this.suvCategoryWithoutId.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].urlImage", Matchers.is(this.suvCategoryWithoutId.getUrlImage())));
    }



    @Test
    @Order(2)
    public void getCategoryWithId1_returnStatusCode200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is(this.suvCategoryWithoutId.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(this.suvCategoryWithoutId.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.urlImage", Matchers.is(this.suvCategoryWithoutId.getUrlImage())));
    }

    @Test
    @Order(3)
    public void getNonExistentCategory_returnStatusCode404() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories/100", "not_found")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> Assertions.assertEquals("Não há nenhum registro com o id " + 100, result.getResolvedException().getMessage()));
    }



    @Test
    @Order(4)
    public void postNewCategory_returnStatusCode201() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.esportivoJsonWithoutId))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is(this.esportivoCategoryWithoutId.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(this.esportivoCategoryWithoutId.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.urlImage", Matchers.is(this.esportivoCategoryWithoutId.getUrlImage())));
    }

    @Test
    @Order(5)
    public void postCategoryWithoutATitle_returnStatusCode422_throwAnUnreportedEssentialFieldException() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonWithoutIdAndTitle))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof UnreportedEssentialFieldException))
                .andExpect(result -> Assertions.assertEquals("Título não informado", result.getResolvedException().getMessage()));
    }

    @Test
    @Order(6)
    public void postCategoryWithARepeatedTitle_returnStatusCode422_throwAnUniqueAttributeValueRepeatedException() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.suvJsonWithoutId))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof UniqueAttributeValueRepeatedException))
                .andExpect(result -> Assertions.assertEquals("Título já existe", result.getResolvedException().getMessage()));
    }



    @Test
    @Order(7)
    public void putCategoryWithId2_returnStatusCode200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.sedanJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(this.sedanCategory.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is(this.sedanCategory.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(this.sedanCategory.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.urlImage", Matchers.is(this.sedanCategory.getUrlImage())));
    }

    @Test
    @Order(8)
    public void putCategoryWithoutId_returnStatusCode422_throwUnreportedEssentialFieldException() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonWithoutIdAndTitle))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof UnreportedEssentialFieldException))
                .andExpect(result -> Assertions.assertEquals("Id não informado", result.getResolvedException().getMessage()));
    }

    @Test
    @Order(9)
    public void putCategoryWithNonExistingId_returnStatusCode404_throwEntityNotFoundException() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.picapeJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> Assertions.assertEquals("Não há nenhum registro com o id " + this.picapeCategory.getId(), result.getResolvedException().getMessage()));
    }



    @Test
    @Order(10)
    public void deleteCategoryWithId1_returnStatusCode200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(11)
    public void deleteCategoryWithNonExistingId_returnStatusCode404_throwEntityNotFoundException() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/categories/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> Assertions.assertEquals("Não há nenhum registro com o id " + 100, result.getResolvedException().getMessage()));
    }


    private void startUsefulObjects() throws JsonProcessingException {
        this.sedanCategory = new Category(1L,
                "sedan",
                "veículo confortável, espaçoso e com potência média/alta, mas baixo",
                "https://i0.wp.com/loucosporcarro.com.br/wp-content/uploads/2017/01/especial-sedas.jpg?resize=800%2C450&ssl=1");
        this.sedanJson = this.objectMapper.writeValueAsString(this.sedanCategory);

        this.picapeCategory = new Category(100L,
                "picape",
                "veículo com potência e rusticidade que possui uma carroceria para transportar objetos",
                "https://i0.wp.com/loucosporcarro.com.br/wp-content/uploads/2017/01/especial-sedas.jpg?resize=800%2C450&ssl=1");
        this.picapeJson = this.objectMapper.writeValueAsString(this.picapeCategory);

        this.suvCategoryWithoutId = new Category();
        this.suvCategoryWithoutId.setTitle("suv");
        this.suvCategoryWithoutId.setDescription("veículo de porte avantajado, espaçoso e com muita potência");
        this.suvCategoryWithoutId.setUrlImage("https://img2.icarros.com/dbimg/imgadicionalnoticia/4/112105_1");
        this.suvJsonWithoutId = this.objectMapper.writeValueAsString(this.suvCategoryWithoutId);

        this.esportivoCategoryWithoutId = new Category();
        this.esportivoCategoryWithoutId.setTitle("esportivo");
        this.esportivoCategoryWithoutId.setDescription("veículo pouco espaçoso, com ótimo design, muita potência e alto consumo");
        this.esportivoCategoryWithoutId.setUrlImage("http://blog.multigrupo.com.br/wp-content/uploads/2019/09/Esportivo-ou-esportivado-1170x545.jpg");
        this.esportivoJsonWithoutId = this.objectMapper.writeValueAsString(this.esportivoCategoryWithoutId);

        this.categoryWithoutIdAndTitle = new Category();
        this.categoryWithoutIdAndTitle.setDescription("Descrição de uma categoria que não possui id nem título");
        this.categoryWithoutIdAndTitle.setUrlImage("url de uma categoria que não possui id nem título");
        this.jsonWithoutIdAndTitle = this.objectMapper.writeValueAsString(this.categoryWithoutIdAndTitle);
    }
}
