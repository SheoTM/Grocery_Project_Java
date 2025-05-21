package org.example.grocery_project.controller;

import org.example.grocery_project.model.Product;
import org.example.grocery_project.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
@SessionAttributes("cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @ModelAttribute("cart")
    List<Product> cart() {                       // tworzy koszyk w sesji
        return new ArrayList<>();
    }

    @PostMapping("/add/{id}")
    public String add(@PathVariable("id") Long id,
                      @ModelAttribute("cart") List<Product> cart,
                      RedirectAttributes flash) {

        if (cartService.add(id, cart)) {
            flash.addFlashAttribute("msg", "Dodano do koszyka");
        } else {
            flash.addFlashAttribute("err", "Produkt nie istnieje");
        }
        return "redirect:/cart";
    }

    @GetMapping
    public String show(@ModelAttribute("cart") List<Product> cart, Model m) {
        m.addAttribute("total", cartService.total(cart));
        return "cart";
    }

    @PostMapping("/checkout")
    public String checkout(SessionStatus status, RedirectAttributes flash) {
        cartService.clear(status);
        flash.addFlashAttribute("msg", "Zamówienie przyjęte!");
        return "redirect:/products";
    }
}

