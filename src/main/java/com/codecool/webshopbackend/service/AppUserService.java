package com.codecool.webshopbackend.service;

import com.codecool.webshopbackend.entity.AppUser;
import com.codecool.webshopbackend.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Autowired
    AppUserRepository userRepository;

    public Long getIdFromUserName(String userName){
        return userRepository.getIdByUserName(userName);
    }

    public Long saveNewUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return getIdFromUserName(user.getUserName());
    }

    public boolean checkIfEmailUnique(String email) {
        return userRepository.findByEmail(email) == null;
    }

    public boolean checkIfUserNameUnique(String username) {
        return userRepository.findByUserName(username).isEmpty();
    }

    public boolean checkIfPhoneNumberUnique(String phone) {
        return userRepository.findByPhoneNumber(phone).isEmpty();
    }
}

