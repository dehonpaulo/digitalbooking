package br.com.digitalbooking.carros.model;

import br.com.digitalbooking.carros.dto.CityDTO;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_city", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "address", nullable = false)
    private String address;
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Product> products;

    public City() {
    }

    public City(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public City(CityDTO cityDTO) {
        this.name = cityDTO.getName();
        this.address = cityDTO.getAddress();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
