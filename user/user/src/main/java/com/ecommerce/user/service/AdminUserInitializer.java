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

    @Value("${ADMIN_USERNAME:admin}")
    private String adminUsername;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Bean
    public CommandLineRunner createAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if(userRepository.findByName(adminUsername).isEmpty()) {
                User admin = new User();
                admin.setName(adminUsername);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole("ADMIN");
                userRepository.save(admin);
                log.info("Default admin user created!!!");

            }
        };
    }
}
