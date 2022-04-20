package br.com.digitalbooking.carros.dto.productDTOs;

import br.com.digitalbooking.carros.dto.AttributeDTO;
import br.com.digitalbooking.carros.dto.CategoryDTO;
import br.com.digitalbooking.carros.dto.CityDTO;
import br.com.digitalbooking.carros.dto.ImageDTO;
import br.com.digitalbooking.carros.model.Product;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDTO {
    private Long id;
    private String model;
    private String brand;
    private String description;
    private Double cost;
    private CategoryDTO category;
    private CityDTO city;
    private AttributeDTO attribute;
    private List<ImageDTO> images;

    public ProductResponseDTO() {
    }

    public ProductResponseDTO(String model, String brand, String description, Double cost, CategoryDTO category, CityDTO city, AttributeDTO attribute, List<ImageDTO> images) {
        this.model = model;
        this.brand = brand;
        this.description = description;
        this.cost = cost;
        this.category = category.noId();
        this.city = city.noId();
        this.attribute = attribute;
        this.images = images;
    }

    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.model = product.getModel();
        this.brand = product.getBrand();
        this.description = product.getDescription();
        this.cost = product.getCost();
        try {
            this.category = new CategoryDTO(product.getCategory()).noId();
            this.city = new CityDTO(product.getCity()).noId();
        } catch(Exception ex) {
            this.category = null;
            this.city = null;
        }
        try {
            this.attribute = new AttributeDTO(product.getAttribute());
        } catch(Exception ex) {
            this.attribute = null;
        }
        try {
            if (product.getImages() != null) {
                this.images = product.getImages().stream().map(image -> {
                    return new ImageDTO(image);
                }).collect(Collectors.toList());
            }
        } catch(Exception ex) {
            this.images = null;
        }
    }

    public Long getId() { return id; };

    public void setId() { this.id = id;}

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category.noId();
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city.noId();
    }

    public AttributeDTO getAttribute() {
        return attribute;
    }

    public void setAttribute(AttributeDTO attribute) {
        this.attribute = attribute;
    }

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }

    public ProductResponseDTO noId() {
        this.id = null;
        return this;
    }
}