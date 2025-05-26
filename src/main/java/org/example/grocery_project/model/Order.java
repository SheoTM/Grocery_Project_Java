/* Order.java */
package org.example.grocery_project.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "orders")            // „order” jest słowem kluczowym w SQL
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private LocalDateTime created = LocalDateTime.now();
    private BigDecimal    total;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderLine> lines;
}
