
package org.example.grocery_project.service;
import org.example.grocery_project.model.Order;
import org.example.grocery_project.model.User;
import lombok.RequiredArgsConstructor;
import org.example.grocery_project.model.*;
import org.example.grocery_project.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.SessionStatus;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final OrderRepository orderRepo;

    public boolean add(Long productId, int qty, List<CartItem> cart) {
        return productRepo.findById(productId).map(prod -> {
            cart.stream()
                    .filter(ci -> ci.getProduct().getId().equals(prod.getId()))
                    .findFirst()
                    .ifPresentOrElse(
                            ci -> ci.setQty(ci.getQty() + qty),
                            () -> cart.add(new CartItem(prod, qty)));
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

    public void updateQty(Long productId, int qty, List<CartItem> cart) {
        cart.removeIf(ci -> {
            if (ci.getProduct().getId().equals(productId)) {
                if (qty <= 0) return true;           // usuń
                ci.setQty(qty);                      // zmień
            }
            return false;
        });
    }

    public Order saveOrder(List<CartItem> cart, String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow();           // jest, bo logged-in

        Order order = new Order();
        order.setUser(user);
        order.setTotal(total(cart));

        List<OrderLine> lines = cart.stream()
                .map(ci -> new OrderLine(null, order,
                        ci.getProduct(),
                        ci.getQty(),
                        ci.lineTotal()))
                .toList();
        order.setLines(lines);

        return orderRepo.save(order);
    }

//    private User ensureUserExists(String username) {
//        return userRepo.findByUsername(username)
//                .orElseGet(() -> userRepo.save(
//                        new User(null, username, "", "ROLE_USER")));
//    }

    public void clear(SessionStatus status) { status.setComplete(); }
}
