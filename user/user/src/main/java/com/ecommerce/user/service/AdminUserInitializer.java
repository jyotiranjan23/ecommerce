package com.ecommerce.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AdminUserInitializer {

    private final String adminUsername =
            System.getenv().getOrDefault("ADMIN_USERNAME", "admin");

    private final String adminPassword =
            System.getenv().getOrDefault("ADMIN_PASSWORD", "admin123");

    private final String adminEmail =
            System.getenv().getOrDefault("ADMIN_EMAIL", "admin@gmail.com");

    @Bean
    public CommandLineRunner createAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if(userRepository.findByName(adminUsername).isEmpty()) {
                User admin = new User();
                admin.setName(adminUsername);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setEmail(adminEmail);
                admin.setRole("ADMIN");
                userRepository.save(admin);
                log.info("Default admin user created!!!");

            }
        };
    }
}
