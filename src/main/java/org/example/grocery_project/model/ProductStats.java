package org.example.grocery_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductStats {
    private String category;
    private long count;
}
