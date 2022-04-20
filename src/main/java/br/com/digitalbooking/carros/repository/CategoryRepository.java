package br.com.digitalbooking.carros.repository;

import br.com.digitalbooking.carros.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByTitleIs(String title);
}