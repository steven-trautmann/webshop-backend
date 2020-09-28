package com.codecool.webshopbackend;

import com.codecool.webshopbackend.entity.AppUser;
import com.codecool.webshopbackend.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class WebShopBackendApplication {

    @Autowired
    AppUserRepository userRepository;

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static void main(String[] args) {
        SpringApplication.run(WebShopBackendApplication.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner initUsers() {
        return args -> {
            userRepository.save(AppUser.builder()
                    .firstName("István")
                    .lastName("Trautmann")
                    .userName("Isti")
                    .birthday(LocalDate.of(2000, 2, 3))
                    .password(passwordEncoder.encode("password2"))
                    .email("isti@gmail.com")
                    .phoneNumber("0670111211")
                    .roles(Arrays.asList("ROLE_USER"))
                    .build());

            userRepository.save(AppUser.builder()
                    .firstName("Zsuzsanna")
                    .lastName("Urbán")
                    .userName("zsuzsi")
                    .birthday(LocalDate.of(2001, 2, 3))
                    .password(passwordEncoder.encode("password3"))
                    .email("zsuzs@gmail.com")
                    .phoneNumber("0670111231")
                    .roles(Arrays.asList("ROLE_USER"))
                    .build());
        };
    }
}
