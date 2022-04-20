package br.com.digitalbooking.carros.repository;

import br.com.digitalbooking.carros.dto.productDTOs.ProductResponseDTO;
import br.com.digitalbooking.carros.model.Booking;
import br.com.digitalbooking.carros.model.Product;
import br.com.digitalbooking.carros.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
    List<Booking> findByProduct(Product product);

}
