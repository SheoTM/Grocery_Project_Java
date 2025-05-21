package org.example.grocery_project.controller;

import org.example.grocery_project.model.Product;
import org.example.grocery_project.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository repo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", repo.findAll());
        model.addAttribute("newProduct", new Product());
        return "products";
    }

    @PostMapping
    public String add(@ModelAttribute("id") Product newProduct) {
        repo.save(newProduct);
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        repo.deleteById(id);
        return "redirect:/products";
    }
}
