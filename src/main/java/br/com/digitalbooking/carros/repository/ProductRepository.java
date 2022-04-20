package br.com.digitalbooking.carros.repository;

import br.com.digitalbooking.carros.model.Category;
import br.com.digitalbooking.carros.model.City;
import br.com.digitalbooking.carros.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCity(City city);
    List<Product> findByCategory(Category category);

    @Query("SELECT p FROM Product p INNER JOIN Booking b ON p.id = b.product.id INNER JOIN City c ON c.id = p" +
          ".city.id WHERE ?1 < b.checkin AND ?2 > b.checkout AND c.name = ?3")
    List<Product> findReservedProductsByDatesAndCities(LocalDate checkIn, LocalDate checkOut,
                                                                   Long city);
}
