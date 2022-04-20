package br.com.digitalbooking.carros.dto.bookingDTOs;

import br.com.digitalbooking.carros.model.Booking;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookingResponseDTO {
    private Long id;
    private String checkIn;
    private String checkOut;
    private String status;
    private String pickUp;
    private Long idUser;
    private Long idProduct;
    DateTimeFormatter parser = DateTimeFormatter.ofPattern("uuuu/MM/dd");

    LocalDate start = LocalDate.parse(checkIn, parser);
    LocalDate end = LocalDate.parse(checkOut, parser );
    public BookingResponseDTO(){
    }

    public BookingResponseDTO(String date, String status, String checkIn, String checkOut, Long idProduct){
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
        this.idProduct = idProduct;
    }

    public BookingResponseDTO(Long id, String checkIn, String checkOut, String status, Long idProduct, Long idUser){
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
        this.idProduct = idProduct;
        this.idUser = idUser;
    }

    public BookingResponseDTO(Booking booking){
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
        this.idProduct = idProduct;
        this.idUser = idUser;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public LocalDate getCheckIn() {
        return LocalDate.parse(checkIn);
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return LocalDate.parse(checkOut);
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPickUp() {
        return pickUp;
    }

    public void setPickUp(String pickUp) {
        this.pickUp = pickUp;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) { this.idUser = idUser; }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) { this.idProduct = idProduct; }
}
