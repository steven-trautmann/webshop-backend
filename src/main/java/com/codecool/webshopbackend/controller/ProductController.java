package com.codecool.webshopbackend.controller;

import com.codecool.webshopbackend.security.JwtTokenServices;
import com.codecool.webshopbackend.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins= "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    AppUserService appUserService;

    @Autowired
    JwtTokenServices jwtTokenServices;

    @GetMapping("/add")
    public String addProduct(@CookieValue(value = "username") String username, @CookieValue(value = "token") String token) {
        System.out.println("reading cookie -------------------");
        System.out.println("username: " + username);
        System.out.println("token: " + token);
        System.out.println("cookie read ----------------------");
        return "its read";
    }

}

