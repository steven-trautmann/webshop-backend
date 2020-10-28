package com.codecool.webshopbackend.service;

import com.codecool.webshopbackend.model.ImageRequestBody;
import com.codecool.webshopbackend.repository.ImageDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageDBRepository imageDBRepository;

    public void saveImage(ImageRequestBody image) {
        imageDBRepository.saveImage(image.getUser_id(), image.getUrl());
    }

    public String getUrlByUserId(Long id) {
        return imageDBRepository.getUrlByUserId(id);
    }

    public void updateImage(ImageRequestBody image) {
        imageDBRepository.updateUrlByUserId(image.getUrl(), image.getUser_id());
    }
}
