package org.example.grocery_project.security;

import lombok.RequiredArgsConstructor;
import org.example.grocery_project.repository.AppUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;

@Configuration
@RequiredArgsConstructor
class UserDetailsConfig {

    private final AppUserRepository repo;

    @Bean
    UserDetailsService userDetailsService() {
        return username -> repo.findByUsername(username)
                .map(u -> User.withUsername(u.getUsername())
                        .password(u.getPassword())      // NoOp
                        .roles(u.getRole().replace("ROLE_", ""))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
