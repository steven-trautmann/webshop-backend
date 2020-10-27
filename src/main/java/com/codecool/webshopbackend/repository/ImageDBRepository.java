package com.codecool.webshopbackend.repository;

import com.codecool.webshopbackend.entity.ImageDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDBRepository extends JpaRepository<ImageDB, Long> {
}
