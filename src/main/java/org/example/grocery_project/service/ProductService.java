package org.example.grocery_project.service;

import lombok.RequiredArgsConstructor;
import org.example.grocery_project.model.Product;
import org.example.grocery_project.model.ProductStats;
import org.example.grocery_project.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;

    public List<Product> getFilteredSortedProducts(String category, String sort) {
        List<Product> products = productRepo.findAll();

        // filter
        if (category != null && !category.isEmpty()) {
            products = products.stream()
                    .filter(p -> category.equalsIgnoreCase(p.getCategory()))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        // sort
        if ("asc".equalsIgnoreCase(sort)) {
            products.sort(Comparator.comparing(Product::getName));
        } else if ("desc".equalsIgnoreCase(sort)) {
            products.sort(Comparator.comparing(Product::getName).reversed());
        }

        return products;
    }

    public Product getById(Long id) {
        return productRepo.findById(id).orElseThrow();
    }

    public void save(Product product) {
        productRepo.save(product);
    }

    public void delete(Long id) {
        productRepo.deleteById(id);
    }

    public List<ProductStats> getStats() {
        List<Product> all = productRepo.findAll();
        Map<String, Long> grouped = all.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));

        return grouped.entrySet().stream()
                .map(e -> new ProductStats(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }
}
