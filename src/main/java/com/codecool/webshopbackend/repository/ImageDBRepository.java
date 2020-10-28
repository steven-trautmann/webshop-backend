package com.codecool.webshopbackend.repository;

import com.codecool.webshopbackend.entity.ImageDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ImageDBRepository extends JpaRepository<ImageDB, Long> {

    @Query(value = "SELECT url FROM imagedb WHERE imagedb.app_user_id = ?1", nativeQuery = true)
    public String getUrlByUserId(Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE imagedb SET url = ?1 WHERE imagedb.app_user_id = ?2", nativeQuery = true)
    void updateUrlByUserId(String url, Long id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO imagedb (app_user_id, url) VALUES (?1, ?2)", nativeQuery = true)
    void saveImage(Long user_id, String url);
}
