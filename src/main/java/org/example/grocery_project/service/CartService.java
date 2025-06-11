package org.example.grocery_project.service;

import lombok.RequiredArgsConstructor;
import org.example.grocery_project.model.CartItem;
import org.example.grocery_project.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.SessionStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

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
                            ci -> ci.setQty(ci.getQty() + qty),
                            () -> cart.add(new CartItem(prod, qty))
                    );
            return true;
        }).orElse(false);
    }

    public BigDecimal total(List<CartItem> cart) {
        return cart.stream()
                .map(CartItem::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clear(SessionStatus status) {
        status.setComplete();
    }

    public void updateCartItemQuantity(Long productId, int quantity, List<CartItem> cart) {
        CartItem item = cart.stream()
                .filter(ci -> ci.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Item not found in cart"));
        item.setQty(quantity);
    }

    public void removeCartItem(Long productId, List<CartItem> cart) {
        cart.removeIf(ci -> ci.getProduct().getId().equals(productId));
    }
}
