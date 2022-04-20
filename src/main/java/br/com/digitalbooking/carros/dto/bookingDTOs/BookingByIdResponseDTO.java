package br.com.digitalbooking.carros.dto.bookingDTOs;

import br.com.digitalbooking.carros.dto.productDTOs.ProductResponseDTO;
import br.com.digitalbooking.carros.dto.UserDTO;
import br.com.digitalbooking.carros.model.Booking;

import java.time.LocalDate;

public class BookingByIdResponseDTO {
    private Long id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String status;
    private String pickUp;
    private UserDTO user;
    private ProductResponseDTO product;

    public BookingByIdResponseDTO(){
    }

    public BookingByIdResponseDTO(Long id, LocalDate checkIn, LocalDate checkOut, String status, String pickUp, ProductResponseDTO product, UserDTO user){
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
        this.pickUp = pickUp;
        this.product = product;
        this.user = user;
    }

    public BookingByIdResponseDTO(Booking booking){
        this.id = booking.getId();
        this.checkIn = booking.getCheckIn();
        this.checkOut = booking.getCheckOut();
        this.status = booking.getStatus();
        this.product = new ProductResponseDTO(booking.getProduct());
        this.user = new UserDTO(booking.getUser());
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) { this.user = user; }

    public ProductResponseDTO getProduct() {
        return product;
    }

    public void setProduct(ProductResponseDTO product) { this.product = product; }
}
