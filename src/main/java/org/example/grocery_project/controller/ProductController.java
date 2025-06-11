package org.example.grocery_project.controller;

import lombok.RequiredArgsConstructor;
import org.example.grocery_project.model.Product;
import org.example.grocery_project.model.ProductStats;
import org.example.grocery_project.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public String listProducts(@RequestParam(defaultValue = "asc") String sort,
                               @RequestParam(required = false) String category,
                               Model model) {
        List<Product> products = productService.getFilteredSortedProducts(category, sort);
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/products/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("actionUrl", "/products");
        return "product-form";
    }

    @PostMapping("/products")
    public String saveProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products/{id}/edit")
    public String editProduct(@PathVariable Long id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        model.addAttribute("actionUrl", "/products/" + id);
        return "product-form";
    }

    @PostMapping("/products/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        product.setId(id);
        productService.save(product);
        return "redirect:/products";
    }

    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products";
    }

    @GetMapping("/products/stats")
    public String productStats(Model model) {
        List<ProductStats> stats = productService.getStats();
        model.addAttribute("stats", stats);
        return "Product-stats";
    }
}
