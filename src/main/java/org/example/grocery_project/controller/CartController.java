package org.example.grocery_project.controller;

import lombok.RequiredArgsConstructor;
import org.example.grocery_project.model.CartItem;
import org.example.grocery_project.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
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
    List<CartItem> cart() { return new ArrayList<>(); }

    @PostMapping("/add/{id}")
    public String add(@PathVariable Long id,
                      @RequestParam int qty,
                      @ModelAttribute("cart") List<CartItem> cart) {
        cartService.add(id, qty, cart);
        return "redirect:/cart";
    }

    @GetMapping
    public String show(@ModelAttribute("cart") List<CartItem> cart, Model m) {
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
