package br.com.digitalbooking.carros;

import br.com.digitalbooking.carros.dto.*;
import br.com.digitalbooking.carros.dto.productDTOs.ProductRequestDTO;
import br.com.digitalbooking.carros.dto.productDTOs.ProductResponseDTO;
import br.com.digitalbooking.carros.model.*;

import java.util.List;

public class TestData {
    public static City city1;
    public static City city2;
    public static City city3;
    public static CityDTO city1DTO;
    public static CityDTO city2DTO;
    public static CityDTO city3DTO;

    public static Category category1;
    public static Category category2;
    public static Category category3;
    public static CategoryDTO category1DTO;
    public static CategoryDTO category2DTO;
    public static CategoryDTO category3DTO;

    public static Image image1;
    public static Image image2;
    public static Image image3;
    public static Image image4;
    public static ImageDTO image1DTO;
    public static ImageDTO image2DTO;
    public static ImageDTO image3DTO;
    public static ImageDTO image4DTO;

    public static Attribute attribute1;
    public static Attribute attribute2;
    public static AttributeDTO attribute1DTO;
    public static AttributeDTO attribute2DTO;

    public static Product product1;
    public static Product product2;
    public static ProductRequestDTO product1RequestDTO;
    public static ProductRequestDTO product2RequestDTO;
    public static ProductResponseDTO product1ResponseDTO;
    public static ProductResponseDTO product2ResponseDTO;

    public static Role roleAdmin;
    public static Role roleUser;

    public static User user1;
    public static User user2;

    public static UserDTO user1DTO;
    public static UserDTO user2DTO;

    public static Booking booking1;
    public static Booking booking2;

    public static void resetCityData() {
        city1 = new City(1L, "Natal", "Barro Neópolis, 3100");
        city2 = new City(2L, "João Pessoa", "Bairro Altiplano, 1206");
        city3 = new City(3L, "Fortaleza", "Bairro Meireles, 793");
        city1DTO = new CityDTO(city1);
        city2DTO = new CityDTO(city2);
        city3DTO = new CityDTO(city3);
    }

    public static void resetCategoryData() {
        category1 = new Category(1L, "esportivo",
                "veículos com alta performance e design diferenciado",
                "https://contagiros.files.wordpress.com/2010/08/izaro-esportivo-eletrico-conceito.jpg");
        category2 = new Category(2L, "suv",
                "veículos de porte avantajado, muito espaçosos e com ótimo desempenho dentro e fora da cidade",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStJ7F0RglLuG7dBm4rqX60C_WEOP-CSTvRwQ&usqp=CAU");
        category3 = new Category(3L, "picape",
                "Caminhonetes com alta performance, cabine espaçosa e confortável e design arrojado",
                "https://img1.icarros.com/dbimg/galeriaimgmodelo/8/135020_1.jpg");
        category1DTO = new CategoryDTO(category1);
        category2DTO = new CategoryDTO(category2);
        category3DTO = new CategoryDTO(category3);
    }

    private static void resetImageData() {
        image1 = new Image(1L, "imagem frontal",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSAm_BSUYginSR8IEQX_7wSES7sP6N5IoFPh40GzfCxZsYUKmStnn3dyY0-JBH0YWZSme0&usqp=CAU",
                null);
        image2 = new Image(2L, "imagem traseira",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRh6URgp5uth1-fMCRp5yLvTx7FHfM1xxH1OA&usqp=CAU",
                null);
        image3 = new Image(3L, "imagem frontal",
                "https://images.noticiasautomotivas.com.br/img/f/BMW-M4-CS-2018-1-1.jpg",
                null);
        image4 = new Image(4L, "imagem traseira",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpKNQlcgK9jPd2ck-Cg99dTl0ozc4CTQjHABqgthAC2mz9o1Xz3eVr3DGyl_p_6rt3tKw&usqp=CAU",
                null);

        image1DTO = new ImageDTO(image1);
        image2DTO = new ImageDTO(image2);
        image3DTO = new ImageDTO(image3);
        image4DTO = new ImageDTO(image4);
    }

    private static void resetAttributeData() {
        attribute1 = new Attribute(1L, "400 HP", "4", "3,7s", "Automático de 7 marchas",
                "Gasolina", "Integral Quattro", "3");
        attribute2 = new Attribute(2L, "480 HP", "4", "4,3s", "Manual",
                "Gasolina", "Integral", "3");
        attribute1DTO = new AttributeDTO(attribute1);
        attribute2DTO = new AttributeDTO(attribute2);
    }

    public static void resetProductData() {
        resetCityData();
        resetCategoryData();
        resetImageData();
        resetAttributeData();

        product1 = new Product(1L, "TT RS", "Audi",
                "O melhor da série TT da Audi Sport. Potência sem limites, um design cativante, além de alta estabilidade e dinâmica de direção",
                450.0, category1, city1, List.of(image1, image2), attribute1);
        product2 = new Product(2L, "M4", "BMW",
                "O cupê de alto desempenho da BMW",
                400.0, category2, city2, List.of(image3, image4), attribute2);

        image1.setProduct(product1);
        image2.setProduct(product1);
        image3.setProduct(product2);
        image4.setProduct(product2);

        product1RequestDTO = new ProductRequestDTO(product1);
        product1ResponseDTO = new ProductResponseDTO(product1);
        product2RequestDTO = new ProductRequestDTO(product2);
        product2ResponseDTO = new ProductResponseDTO(product2);
    }

    private static void resetRoleData() {
        roleUser = new Role(1L, "ROLE_USER", null);
        roleAdmin = new Role(2L, "ROLE_ADMIN", null);
    }

    public static void resetUserData() {
        user1 = new User(1L, "paulo", "dehon", "paulodehon@email.com", "", roleUser);
        user2 = new User(2L, "Eduardo", "Marcondes", "eduardomarcondes@email.com", "", roleAdmin);

        user1DTO = new UserDTO(user1);
        user2DTO = new UserDTO(user2);

        roleUser.setUsers(List.of(user1));
        roleAdmin.setUsers(List.of(user2));
    }
}
