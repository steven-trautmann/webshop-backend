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
        //check if not nullable fields are null or ""
        if (user.checkIfNotNullableFieldsAreNull()){
            Map<Object, Object> model = new HashMap<>();
            model.put("nullableError", "confirmed");
            return ResponseEntity.badRequest().body(model);
        }

        //check email validity
        String re = "^[\\w\\-.+_%]+@[\\w\\.\\-]+\\.[A-Za-z0-9]{2,}$";
        if (!user.getEmail().matches(re)){
            Map<Object, Object> model = new HashMap<>();
            model.put("emailValidityError", "confirmed");
            return ResponseEntity.badRequest().body(model);
        }

        //check email uniqueness
        if (!appUserService.checkIfEmailUnique(user.getEmail())){
            Map<Object, Object> model = new HashMap<>();
            model.put("emailUniquenessError", "confirmed");
            return ResponseEntity.badRequest().body(model);
        }

        //check username is unique
        if (!appUserService.checkIfUserNameUnique(user.getUserName())){
            Map<Object, Object> model = new HashMap<>();
            model.put("userNameUniquenessError", "confirmed");
            return ResponseEntity.badRequest().body(model);
        }

        //check phone number is unique
        if (!appUserService.checkIfPhoneNumberUnique(user.getPhoneNumber())){
            Map<Object, Object> model = new HashMap<>();
            model.put("phoneNumberUniquenessError", "confirmed");
            return ResponseEntity.badRequest().body(model);
        }

        //check birthday is between 1900 and today
        if (!appUserService.checkIfBirthdayIsInPreferredRange(user.getBirthday())){
            Map<Object, Object> model = new HashMap<>();
            model.put("birthDayOutOfRangeError", "confirmed");
            return ResponseEntity.badRequest().body(model);
        }

        user.setRoles(Arrays.asList("ROLE_USER"));
        Long id = null;

        //database error
        try {
            id = appUserService.saveNewUser(user);
        } catch (Exception e) {
            Map<Object, Object> model = new HashMap<>();
            model.put("error", "error occurred when trying to save user to the database!");
            return ResponseEntity.status(500).body(model);
        }

        String token = jwtTokenServices.createToken(user.getUserName(), user.getRoles());

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

        Map<Object, Object> model = new HashMap<>();
        model.put("username", user.getUserName());
        model.put("roles", user.getRoles());
        model.put("userId", id);

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

            // create a cookie for username
            Cookie cookieUserName = new Cookie("username", username);
            cookieUserName.setMaxAge(7 * 24 * 60 * 60);
            cookieUserName.setHttpOnly(true);
            cookieUserName.setPath("/");
            response.addCookie(cookieUserName);

            // create a cookie for the JWT Token
            Cookie cookieToken = new Cookie("token", token);
            cookieToken.setMaxAge(7 * 24 * 60 * 60);
            cookieToken.setHttpOnly(true);
            cookieToken.setPath("/");
            response.addCookie(cookieToken);

            Long id = appUserService.getIdFromUserName(username);

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("roles", roles);
            model.put("userId", id);
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response){
    Cookie cookieUserName = new Cookie("username", null);
    cookieUserName.setMaxAge(0);
    cookieUserName.setHttpOnly(true);
    cookieUserName.setPath("/");

    response.addCookie(cookieUserName);

    Cookie cookieToken = new Cookie("token", null);
    cookieToken.setMaxAge(0);
    cookieToken.setHttpOnly(true);
    cookieToken.setPath("/");
    response.addCookie(cookieToken);

    return "logged out";
    }

    @PostMapping("/check-email")
    public boolean checkEmailUniqueness(@RequestBody Map<String, Object> data){
        return appUserService.checkIfEmailUnique(data.get("email").toString());
    }

    @PostMapping("/check-username")
    public boolean checkUserNameUniqueness(@RequestBody Map<String, Object> data){
        return appUserService.checkIfUserNameUnique(data.get("username").toString());
    }

    @PostMapping("/check-phone")
    public boolean checkPhoneNumberUniqueness(@RequestBody Map<String, Object> data){
        return appUserService.checkIfPhoneNumberUnique(data.get("phone").toString());
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
