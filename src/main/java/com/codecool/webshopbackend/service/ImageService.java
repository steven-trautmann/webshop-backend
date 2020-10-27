package com.codecool.webshopbackend.service;

import com.codecool.webshopbackend.entity.ImageDB;
import com.codecool.webshopbackend.repository.ImageDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ImageService {

    @Autowired
    private ImageDBRepository imageDBRepository;

    public void saveImage(ImageDB imageDB) {
        imageDBRepository.save(imageDB);
    }

    public String getUrlByUserName(String name) {
        return imageDBRepository.getUrlByName(name);
    }

    public void updateImage(Map<String, String> imageDBImitator) {
        String name = imageDBImitator.get("name");
        String url = imageDBImitator.get("url");
        imageDBRepository.updateUrlByName(url, name);
    }
}
