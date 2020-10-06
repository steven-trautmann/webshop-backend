package com.codecool.webshopbackend.repository;

import com.codecool.webshopbackend.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser getAppUserById(Long id);
    Optional<AppUser> findByUserName(String userName);

    @Query("SELECT id FROM AppUser WHERE userName=:username")
    Long getIdByUserName(@Param("username") String username);

    Optional<AppUser> findByPhoneNumber(String phoneNumber);

    AppUser findByEmail(String email);
}
