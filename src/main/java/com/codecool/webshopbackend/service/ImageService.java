package com.codecool.webshopbackend.service;

import com.codecool.webshopbackend.entity.ImageDB;
import com.codecool.webshopbackend.repository.ImageDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageDBRepository imageDBRepository;

    public void saveImage(ImageDB imageDB) {
        imageDBRepository.save(imageDB);
    }
}
