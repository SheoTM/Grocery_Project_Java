/* OrderLine.java */
package org.example.grocery_project.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "order_line")
public class OrderLine {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "product_id")
    private Product product;

    private Integer qty;
    private BigDecimal lineTotal;
}
