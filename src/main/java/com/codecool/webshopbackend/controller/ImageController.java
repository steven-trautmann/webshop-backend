package com.codecool.webshopbackend.controller;

import com.codecool.webshopbackend.model.ImageRequestBody;
import com.codecool.webshopbackend.service.AppUserService;
import com.codecool.webshopbackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.Map;

@RestController
@CrossOrigin(origins= "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/img")
public class ImageController {

    @Autowired
    ImageService imageService;

    @Autowired
    private AppUserService appUserService;

    @PostMapping("/upload")
    public void uploadFile(@RequestBody ImageRequestBody image) throws Exception {
        if (image.fieldsAreNotNull() && image.imageTypeIsValid()){
            imageService.saveImage(image);
        } else {
            throw new Exception("Incorrect request.");
        }
    }

    @PostMapping("/change")
    public void changeFile(@RequestBody ImageRequestBody image) throws Exception {
        if (image.fieldsAreNotNull() && image.imageTypeIsValid()){
            imageService.updateImage(image);
        } else {
            throw new Exception("Incorrect request.");
        }
    }

    @GetMapping("/file-name")
    public String getFileName(Principal principal){
        return appUserService.getIdFromUserName(principal.getName()).toString(); //This has three purpose: get the filename && check if the backend is working && the user is logged in!
    }

    @GetMapping("/profile-img-data")
    public Map<String, Object> getProfileImgData(Principal principal){
        return imageService.getProfileImgDataById(appUserService.getIdFromUserName(principal.getName()));
    }
}
