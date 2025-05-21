package org.example.grocery_project.controller;

import lombok.RequiredArgsConstructor;
import org.example.grocery_project.model.Product;
import org.example.grocery_project.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository repo;

    @GetMapping
    public String list(Model m) {
        m.addAttribute("products", repo.findAll());
        return "products";
    }

    @GetMapping("/new")
    public String createForm(Model m) {
        m.addAttribute("product", new Product());
        m.addAttribute("actionUrl", "/products");
        return "product-form";
    }

    @PostMapping
    public String create(@ModelAttribute Product product) {
        repo.save(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model m) {
        Product p = repo.findById(id).orElseThrow();
        m.addAttribute("product", p);
        m.addAttribute("actionUrl", "/products/" + id);
        return "product-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Product product) {
        product.setId(id);
        repo.save(product);
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/products";
    }
}
