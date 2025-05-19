package org.example.grocery_project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "cart")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<CartItem> cartItems;

    public double getTotalPrice() {
        double total = 0;
        for (CartItem cartItem : cartItems) {
            total += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        return total;
    }
}
