package br.com.digitalbooking.carros.dto.productDTOs;

import br.com.digitalbooking.carros.dto.AttributeDTO;
import br.com.digitalbooking.carros.dto.ImageDTO;
import br.com.digitalbooking.carros.model.Product;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductRequestDTO {
    private Long id;
    private String model;
    private String brand;
    private String description;
    private Double cost;
    private Long idCategory;
    private Long idCity;
    private AttributeDTO attribute;
    private List<ImageDTO> images;

    public ProductRequestDTO(){
    }

    public ProductRequestDTO(Long id, String model, String brand, String description, Double cost, Long idCategory, Long idCity, AttributeDTO attribute, List<ImageDTO> images) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.description = description;
        this.cost = cost;
        this.idCategory = idCategory;
        this.idCity = idCity;
        this.attribute = attribute;
        this.images = images;
    }

    public ProductRequestDTO(Product product) {
        this.id = product.getId();
        this.model = product.getModel();
        this.brand = product.getBrand();
        this.description = product.getDescription();
        this.cost = product.getCost();
        this.idCategory = product.getCategory().getId();
        this.idCity = product.getCity().getId();
        this.attribute = new AttributeDTO(product.getAttribute());
        if(!product.getImages().equals(null) && !product.getImages().isEmpty()) {
            this.images = product.getImages().stream().map(image -> {
                return new ImageDTO(image);
            }).collect(Collectors.toList());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public Long getIdCity() {
        return idCity;
    }

    public void setIdCity(Long idCity) {
        this.idCity = idCity;
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

    public ProductRequestDTO noId() {
        this.id = null;
        return this;
    }
}
