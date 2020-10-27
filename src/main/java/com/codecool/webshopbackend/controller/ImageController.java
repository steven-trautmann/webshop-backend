package com.codecool.webshopbackend.controller;

import com.codecool.webshopbackend.entity.ImageDB;
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

    @PostMapping("/upload")
    public void uploadFile(@RequestBody ImageDB imageDB) {
        imageService.saveImage(imageDB);
    }

    //imitate ImageDB as its value is set to unique, so it cannot be that object
    @PostMapping("/change")
    public void changeFile(@RequestBody Map<String, String> imageDBImitator) {
        imageService.updateImage(imageDBImitator);
    }

    @GetMapping("/file-name")
    public String getFileName(Principal principal){
        return principal.getName(); //This has three purpose: get the filename && check if the backend is working && the user is logged in!
    }

    @GetMapping("/profile-url")
    public String getProfileUrl(Principal principal){
        return imageService.getUrlByUserName(principal.getName());
    }
    

}
