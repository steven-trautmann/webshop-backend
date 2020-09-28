package com.codecool.webshopbackend.repository;

import com.codecool.webshopbackend.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser getAppUserById(Long id);
    Optional<AppUser> findByUserName(String userName);
}
