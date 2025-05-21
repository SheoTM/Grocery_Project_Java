package org.example.grocery_project.controller;

import org.example.grocery_project.model.Product;
import org.example.grocery_project.repository.ProductRepository;
import org.example.grocery_project.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
@SessionAttributes("cart")
@RequiredArgsConstructor
public class CartController {

    private final ProductRepository repo;
    private final CartService cartService;

    @ModelAttribute("cart")
    public List<Product> cart() {
        return new ArrayList<>();
    }

    @PostMapping("/add/{id}")
    public String add(@PathVariable("id") Long id, @ModelAttribute("cart") List<Product> cart) {
        repo.findById(id).ifPresent(cart::add);
        return "redirect:/cart";
    }

    @GetMapping
    public String show(@ModelAttribute("cart") List<Product> cart, Model model) {
        BigDecimal total = cartService.total(cart);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/checkout")
    public String checkout(SessionStatus status) {
        status.setComplete();
        return "redirect:/products";
    }
}
