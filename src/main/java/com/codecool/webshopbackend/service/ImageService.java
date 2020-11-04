package com.codecool.webshopbackend.service;

import com.codecool.webshopbackend.entity.ImageDB;
import com.codecool.webshopbackend.model.ImageRequestBody;
import com.codecool.webshopbackend.repository.ImageDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageDBRepository imageDBRepository;

    public void saveImage(ImageRequestBody image) {
        imageDBRepository.saveImage(image.getUser_id(), image.getUrl(), image.getImageType());
    }

    public Map<String, Object> getProfileImgDataById(Long id) {
        Optional<ImageDB> imageEntityOptional = imageDBRepository.findById(id);
        Map<String, Object> imgData = new HashMap<>();
        if (imageEntityOptional.isPresent()){
            ImageDB imageEntity = imageEntityOptional.get();
            imgData.put("url", imageEntity.getUrl());
            imgData.put("type", imageEntity.getImageType());
        } else {
            imgData.put("url", "");
            imgData.put("type", "");
        }
        return imgData;
    }

    public void updateImage(ImageRequestBody image) {
        imageDBRepository.updateUrlByUserId(image.getUrl(), image.getImageType(), image.getUser_id());
    }
}
