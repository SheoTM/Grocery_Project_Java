package org.example.grocery_project.controller;

import lombok.RequiredArgsConstructor;
import org.example.grocery_project.model.Product;
import org.example.grocery_project.model.ProductStats;
import org.example.grocery_project.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepo;

    // Display a list of products with optional sorting
    @GetMapping("/products")
    public String listProducts(@RequestParam(defaultValue = "asc") String sort,
                               @RequestParam(required = false) String category,
                               Model model) {
        List<Product> products = productRepo.findAll();

        // Filter by category if provided
        if (category != null && !category.isEmpty()) {
            products = products.stream()
                    .filter(p -> category.equalsIgnoreCase(p.getCategory()))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        // Sort products by name
        if ("asc".equalsIgnoreCase(sort)) {
            products.sort(Comparator.comparing(Product::getName));
        } else if ("desc".equalsIgnoreCase(sort)) {
            products.sort(Comparator.comparing(Product::getName).reversed());
        }

        model.addAttribute("products", products);
        return "products";
    }

    // Show form to add a new product
    @GetMapping("/products/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("actionUrl", "/products");
        return "product-form";
    }

    // Save created product
    @PostMapping("/products")
    public String saveProduct(@ModelAttribute Product product) {
        productRepo.save(product);
        return "redirect:/products";
    }

    // Show form to edit an existing product
    @GetMapping("/products/{id}/edit")
    public String editProduct(@PathVariable Long id, Model model) {
        Product product = productRepo.findById(id).orElseThrow();
        model.addAttribute("product", product);
        model.addAttribute("actionUrl", "/products/" + id);
        return "product-form";
    }

    // Save updates to an existing product
    @PostMapping("/products/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        product.setId(id);
        productRepo.save(product);
        return "redirect:/products";
    }

    // Delete a product by ID
    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productRepo.deleteById(id);
        return "redirect:/products";
    }

    // Display basic product statistics
    @GetMapping("/products/stats")
    public String productStats(Model model) {
        List<Product> all = productRepo.findAll();

        // Group products by category and count how many in each
        Map<String, Long> grouped = all.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));

        List<ProductStats> stats = grouped.entrySet().stream()
                .map(e -> new ProductStats(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        model.addAttribute("stats", stats);
        return "product-stats"; // Return the view: product-stats.html
    }
}
