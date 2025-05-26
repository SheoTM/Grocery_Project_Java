package org.example.grocery_project.model;

import jakarta.persistence.*;
import lombok.*;

/* AppUser.java */
@Entity @Table(name = "APP_USER")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;
    private String role;          // ROLE_USER / ROLE_ADMIN
}
