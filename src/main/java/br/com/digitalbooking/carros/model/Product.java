package br.com.digitalbooking.carros.model;

import org.w3c.dom.Attr;

import javax.persistence.*;
import java.awt.print.Book;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product", nullable = false)
    private Long id;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "description")
    private String description;

    @Column(name = "cost")
    private Double cost;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id_city")
    private City city;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "id_attribute")
    private Attribute attribute;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    public Product() {
    }

    public Product(Long id, String model, String brand, String description, Double cost, Category category, City city, List<Image> images, Attribute attribute) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.description = description;
        this.cost = cost;
        this.category = category;
        this.city = city;
        this.images = images;
        this.attribute = attribute;
    }

    public Product(String model, String brand, String description, Category category, City city, List<Image> images, Attribute attribute) {
        this.model = model;
        this.brand = brand;
        this.description = description;
        this.category = category;
        this.city = city;
        this.images = images;
        this.attribute = attribute;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
}
