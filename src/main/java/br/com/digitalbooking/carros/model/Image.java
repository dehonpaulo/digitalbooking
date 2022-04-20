package br.com.digitalbooking.carros.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image", nullable = false)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "url_image", nullable = false)
    private String urlImage;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    public Image() {
    }

    public Image(Long id, String title, String urlImage, Product product) {
        this.id = id;
        this.title = title;
        this.urlImage = urlImage;
        this.product = product;
    }

    public Image(String title, String urlImage, Product product) {
        this.title = title;
        this.urlImage = urlImage;
        this.product = product;
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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
