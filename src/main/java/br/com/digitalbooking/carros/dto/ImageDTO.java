package br.com.digitalbooking.carros.dto;

import br.com.digitalbooking.carros.model.Image;

public class ImageDTO {
    private String title;
    private String urlImage;

    public ImageDTO() {
    }

    public ImageDTO(String title, String urlImage) {
        this.title = title;
        this.urlImage = urlImage;
    }

    public ImageDTO(Image image) {
        this.title = image.getTitle();
        this.urlImage = image.getUrlImage();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
