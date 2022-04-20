package br.com.digitalbooking.carros.dto;

import br.com.digitalbooking.carros.model.Attribute;

import javax.persistence.Column;

public class AttributeDTO {
    private String power;
    private String seats;
    private String acceleration;
    private String transmission;
    private String gas;
    private String tcs;
    private String doors;

    public AttributeDTO() {
    }

    public AttributeDTO(String power, String seats, String acceleration, String transmission, String gas, String tcs, String doors) {
        this.power = power;
        this.seats = seats;
        this.acceleration = acceleration;
        this.transmission = transmission;
        this.gas = gas;
        this.tcs = tcs;
        this.doors = doors;
    }

    public AttributeDTO(Attribute attribute) {
        this.power = attribute.getPower();
        this.seats = attribute.getSeats();
        this.acceleration = attribute.getAcceleration();
        this.transmission = attribute.getTransmission();
        this.gas = attribute.getGas();
        this.tcs = attribute.getTcs();
        this.doors = attribute.getDoors();
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