
package org.example.grocery_project.controller;

import lombok.RequiredArgsConstructor;
import org.example.grocery_project.model.CartItem;
import org.example.grocery_project.service.CartService;
import org.springframework.security.core.Authentication;
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

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam int qty,
                         @ModelAttribute("cart") List<CartItem> cart) {
        cartService.updateQty(id, qty, cart);
        return "redirect:/cart";
    }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable Long id,
                         @ModelAttribute("cart") List<CartItem> cart) {
        cartService.remove(id, cart);          // ← miałeś już w serwisie
        return "redirect:/cart";
    }


    @PostMapping("/checkout")
    public String checkout(@ModelAttribute("cart") List<CartItem> cart,
                           SessionStatus status,
                           RedirectAttributes flash,
                           Authentication auth) {
        var order = cartService.saveOrder(cart, auth.getName());
        cartService.clear(status);
        flash.addFlashAttribute("msg", "Zamówienie #" + order.getId() +
                " przyjęte. Łącznie " + order.getTotal() + " zł");
        return "redirect:/products";
    }
}

