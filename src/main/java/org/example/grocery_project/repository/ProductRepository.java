package org.example.grocery_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.grocery_project.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
