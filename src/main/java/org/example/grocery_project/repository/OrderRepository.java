
package org.example.grocery_project.repository;

import org.example.grocery_project.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
