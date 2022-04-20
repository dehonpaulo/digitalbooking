package br.com.digitalbooking.carros.dto;

import br.com.digitalbooking.carros.model.Category;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {
    private Long id;
    private String title;
    private String description;
    private String urlImage;

    public CategoryDTO(){
    }

    public CategoryDTO(Long id, String title, String description, String urlImage) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.urlImage = urlImage;
    }

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.description = category.getDescription();
        this.urlImage = category.getUrlImage();
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

    public CategoryDTO noId() {
        this.id = null;
        return this;
    }
}
