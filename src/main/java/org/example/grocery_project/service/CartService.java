package org.example.grocery_project.service;

import lombok.RequiredArgsConstructor;
import org.example.grocery_project.model.CartItem;
import org.example.grocery_project.model.Product;
import org.example.grocery_project.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.SessionStatus;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepo;

    public boolean add(Long productId, int qty, List<CartItem> cart) {
        return productRepo.findById(productId).map(prod -> {
            cart.stream()
                    .filter(ci -> ci.getProduct().getId().equals(prod.getId()))
                    .findFirst()
                    .ifPresentOrElse(
                            ci -> ci.setQty(ci.getQty() + qty),           // już jest
                            () -> cart.add(new CartItem(prod, qty)));     // nowy
            return true;
        }).orElse(false);
    }

    public boolean remove(Long productId, List<CartItem> cart) {
        return cart.removeIf(ci -> ci.getProduct().getId().equals(productId));
    }

    public BigDecimal total(List<CartItem> cart) {
        return cart.stream()
                .map(CartItem::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clear(SessionStatus status) { status.setComplete(); }
}
