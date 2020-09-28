package com.codecool.webshopbackend.controller;

import com.codecool.webshopbackend.entity.AppUser;
import com.codecool.webshopbackend.security.JwtTokenServices;
import com.codecool.webshopbackend.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins= "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/user")
public class UserController {

    @Autowired
    AppUserService appUserService;

    @Autowired
    JwtTokenServices jwtTokenServices;

    private final AuthenticationManager authenticationManager;

    public UserController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody AppUser user, HttpServletResponse response){
        user.setRoles(Arrays.asList("ROLE_USER"));
        Long id = appUserService.saveNewUser(user);

        String token = jwtTokenServices.createToken(user.getUserName(), user.getRoles());
        Map<Object, Object> model = new HashMap<>();
        model.put("username", user.getUserName());
        model.put("roles", user.getRoles());
        model.put("status", "ok");
        model.put("token", token);
        model.put("userId", id);

        // create a cookie
        Cookie cookieUserName = new Cookie("username", user.getUserName());
        cookieUserName.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        cookieUserName.setHttpOnly(true); //client-side cannot react it
        cookieUserName.setPath("/"); // global
        //add cookie to response
        response.addCookie(cookieUserName);

        // create a cookie
        Cookie cookieToken = new Cookie("token", token);
        cookieToken.setMaxAge(7 * 24 * 60 * 60);
        cookieToken.setHttpOnly(true);
        cookieToken.setPath("/");
        response.addCookie(cookieToken);

        return ResponseEntity.ok(model);
    }

    @PostMapping("/login")
    public ResponseEntity<?> logUserIn(@RequestBody Map<String, Object> data, HttpServletResponse response){
        try {
            String username = data.get("username").toString();
            String password = data.get("password").toString();

            // authenticationManager.authenticate calls loadUserByUsername in CustomUserDetailsService
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = jwtTokenServices.createToken(username, roles);

            // create a cookie
            Cookie cookieUserName = new Cookie("username", username);
            cookieUserName.setMaxAge(7 * 24 * 60 * 60);
            cookieUserName.setHttpOnly(true);
            cookieUserName.setPath("/");
            response.addCookie(cookieUserName);

            // create a cookie
            Cookie cookieToken = new Cookie("token", token);
            cookieToken.setMaxAge(7 * 24 * 60 * 60);
            cookieToken.setHttpOnly(true);
            cookieToken.setPath("/");
            response.addCookie(cookieToken);

            Long id = appUserService.getIdFromUserName(username);

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("roles", roles);
            model.put("token", token);
            model.put("userId", id);
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response){
    Cookie cookie = new Cookie("username", null);
    cookie.setMaxAge(0);
    cookie.setHttpOnly(true);
    cookie.setPath("/");

    response.addCookie(cookie);

    return "logged out";
    }

    @GetMapping("/read-cookies")
    public String readAllCookie(HttpServletRequest request) {
        System.out.println("reading cookie -------------------");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            System.out.println(Arrays.stream(cookies)
                    .map(c -> c.getName() + "=" + c.getValue()).collect(Collectors.joining(", ")));
        }
        System.out.println("cookie read ----------------------");
        return "its read";
    }

}
