package br.com.digitalbooking.carros.model;

import br.com.digitalbooking.carros.dto.CategoryDTO;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category", nullable = false)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "url_image")
    private String urlImage;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

    public Category() {
    }

    public Category(CategoryDTO categoryDTO) {
        this.id = categoryDTO.getId();
        this.title = categoryDTO.getTitle();
        this.description = categoryDTO.getDescription();
        this.urlImage = categoryDTO.getUrlImage();
    }

    public Category(Long id, String title, String description, String urlImage) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.urlImage = urlImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

}
