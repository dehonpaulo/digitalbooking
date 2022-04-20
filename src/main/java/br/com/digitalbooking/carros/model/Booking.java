package br.com.digitalbooking.carros.model;

import br.com.digitalbooking.carros.dto.bookingDTOs.BookingRequestDTO;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_booking", nullable = false)
    private Long id;

    @Column(name = "checkin", nullable = false)
    private LocalDate checkin;

    @Column(name = "checkout", nullable = false)
    private LocalDate checkout;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "pick_up")
    private String pickUp;

    @ManyToOne
    @JoinColumn(name = "id_user",  nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_product",  nullable = false)
    private Product product;

    public Booking(){
    }

    public Booking(Long id, String date, String status, String pickUp, LocalDate checkIn, LocalDate checkOut, User user, Product product) {
        this.id = id;
        this.checkin = checkIn;
        this.checkout = checkOut;
        this.status = status;
        this.pickUp = pickUp;
        this.user = user;
        this.product = product;
    }

    public Booking(String date, String status, String pickUp, LocalDate checkIn, LocalDate checkOut, User user, Product product) {
        this.checkin = checkIn;
        this.checkout = checkOut;
        this.status = status;
        this.pickUp = pickUp;
        this.user = user;
        this.product = product;
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCheckIn() {
        return checkin;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkin = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkout;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkout = checkOut;
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

    public User getUser() {
        return user;
    }

    public void setUser(User User) { this.user = User; }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) { this.product = product; }
}
