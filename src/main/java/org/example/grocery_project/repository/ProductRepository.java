
package org.example.grocery_project.repository;

import org.example.grocery_project.model.Product;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingIgnoreCase(String q, Pageable pageable);

    Page<Product> findByPriceBetween(BigDecimal min, BigDecimal max, Pageable pageable);
}
