package org.example.grocery_project.service;

import org.springframework.stereotype.Service;
import org.example.grocery_project.model.Product;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    public BigDecimal total(List<Product> items) {
        return items.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
