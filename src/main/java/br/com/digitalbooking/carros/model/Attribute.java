package br.com.digitalbooking.carros.model;

import br.com.digitalbooking.carros.dto.AttributeDTO;

import javax.persistence.*;

@Entity
@Table(name = "attributes")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_attribute", nullable = false)
    private Long id;
    @Column
    private String power;
    @Column
    private String seats;
    @Column
    private String acceleration;
    @Column
    private String transmission;
    @Column
    private String gas;
    @Column
    private String tcs;
    @Column
    private String doors;

    public Attribute() {
    }

    public Attribute(Long id, String power, String seats, String acceleration, String transmission, String gas, String tcs, String doors) {
        this.id = id;
        this.power = power;
        this.seats = seats;
        this.acceleration = acceleration;
        this.transmission = transmission;
        this.gas = gas;
        this.tcs = tcs;
        this.doors = doors;
    }

    public Attribute(AttributeDTO attributeDTO) {
        this.power = attributeDTO.getPower();
        this.seats = attributeDTO.getSeats();
        this.acceleration = attributeDTO.getAcceleration();
        this.transmission = attributeDTO.getTransmission();
        this.gas = attributeDTO.getGas();
        this.tcs = attributeDTO.getTcs();
        this.doors = attributeDTO.getDoors();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(String acceleration) {
        this.acceleration = acceleration;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getTcs() {
        return tcs;
    }

    public void setTcs(String tcs) {
        this.tcs = tcs;
    }

    public String getDoors() {
        return doors;
    }

    public void setDoors(String doors) {
        this.doors = doors;
    }
}
