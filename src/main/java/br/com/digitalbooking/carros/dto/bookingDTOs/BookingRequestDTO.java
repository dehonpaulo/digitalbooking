package br.com.digitalbooking.carros.dto.bookingDTOs;


import java.time.LocalDate;

public class BookingRequestDTO {
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String pickUp;
    private Long idUser;
    private Long idProduct;

    public BookingRequestDTO(){
    }

    public BookingRequestDTO(String pickUp, LocalDate checkIn, LocalDate checkOut, Long idUser, Long idProduct){
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.pickUp = pickUp;
        this.idUser = idUser;
        this.idProduct = idProduct;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
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
