package com.codecool.webshopbackend.controller;

import com.codecool.webshopbackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins= "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/img")
public class ImageController {
    private static String imageDirectory = System.getProperty("user.dir") + "/images/";

    @Autowired
    ImageService imageService;

    @PostMapping(value="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) {
        makeDirectoryIfNotExist(imageDirectory);
        if (file.getContentType() == null || !file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")){
            Map<Object, Object> model = new HashMap<>();
            model.put("UnsupportedTypeError", "confirmed");
            return ResponseEntity.badRequest().body(model);
        }

        Path fileNamePath = Paths.get(imageDirectory, file.getOriginalFilename());
        try {
            Files.write(fileNamePath, file.getBytes());
            return ResponseEntity.ok().build();
        } catch (IOException ex) {
            return new ResponseEntity<>("Image is not uploaded", HttpStatus.BAD_REQUEST);
        }
    }

    private void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
