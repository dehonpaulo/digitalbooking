package br.com.digitalbooking.carros.dto;

import br.com.digitalbooking.carros.model.City;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityDTO {
    private Long id;
    private String name;
    private String address;

    public CityDTO() {
    }

    public CityDTO(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public CityDTO(City city) {
        this.id = city.getId();
        this.name = city.getName();
        this.address = city.getAddress();
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

    public CityDTO noId() {
        this.id = null;
        return this;
    }
}