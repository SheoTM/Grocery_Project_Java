package org.example.grocery_project.controller;

import lombok.RequiredArgsConstructor;
import org.example.grocery_project.model.CartItem;
import org.example.grocery_project.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
@SessionAttributes("cart")
public class CartController {

    private final CartService cartService;

    // Create a new cart
    @ModelAttribute("cart")
    public List<CartItem> cart() {
        return new java.util.ArrayList<>();
    }

    // Add a product to the cart or increase quantity
    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable Long id,
                            @RequestParam(defaultValue = "1") int quantity,
                            @ModelAttribute("cart") List<CartItem> cart) {
        cartService.add(id, quantity, cart);
        return "redirect:/products";
    }

    // Display the cart and total price
    @GetMapping
    public String showCart(@ModelAttribute("cart") List<CartItem> cart,
                           org.springframework.ui.Model model) {
        model.addAttribute("total", cartService.total(cart));
        return "cart";
    }

    // Clear the cart
    @PostMapping("/checkout")
    public String checkout(SessionStatus status) {
        cartService.clear(status);
        return "redirect:/products";
    }

    // Update quantity
    @PostMapping("/update/{id}")
    public String updateQuantity(@PathVariable Long id,
                            @RequestParam int quantity,
                            @ModelAttribute("cart") List<CartItem> cart) {
        cartService.updateCartItemQuantity(id, quantity, cart);
        return "redirect:/cart";
    }

    // Remove item from cart
    @PostMapping("/remove/{id}")
    public String removeItem(@PathVariable Long id,
                             @ModelAttribute("cart") List<CartItem> cart) {
        cartService.removeCartItem(id, cart);
        return "redirect:/cart";
    }
}
