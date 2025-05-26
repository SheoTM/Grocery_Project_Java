package org.example.grocery_project.repository;

import org.example.grocery_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}