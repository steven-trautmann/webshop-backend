package com.codecool.webshopbackend.repository;

import com.codecool.webshopbackend.entity.ImageDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ImageDBRepository extends JpaRepository<ImageDB, Long> {

    @Query(value = "SELECT url FROM imagedb WHERE imagedb.name = ?1", nativeQuery = true)
    public String getUrlByName(String name);

    @Transactional
    @Modifying
    @Query(value = "UPDATE imagedb SET url = ?1 WHERE imagedb.name = ?2", nativeQuery = true)
    void updateUrlByName(String url, String name);
}
