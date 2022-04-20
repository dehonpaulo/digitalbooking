package br.com.digitalbooking.carros.repository;

import br.com.digitalbooking.carros.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
}
