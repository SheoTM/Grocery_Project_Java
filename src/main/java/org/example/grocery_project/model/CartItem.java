package org.example.grocery_project.model;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    private Product product;
    private int     qty;

    public BigDecimal lineTotal() {
        return product.getPrice().multiply(BigDecimal.valueOf(qty));
    }
}
