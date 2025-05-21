package org.example.grocery_project.service;

import lombok.RequiredArgsConstructor;
import org.example.grocery_project.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.example.grocery_project.model.Product;
import org.springframework.web.bind.support.SessionStatus;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepo;

    public boolean add(Long productId, List<Product> cart) {
        return productRepo.findById(productId)
                .map(cart::add)
                .isPresent();
    }

    public boolean remove(Long productId, List<Product> cart) {
        return cart.removeIf(p -> p.getId().equals(productId));
    }

    public BigDecimal total(List<Product> cart) {
        return cart.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clear(SessionStatus status) {
        status.setComplete();
    }
}
