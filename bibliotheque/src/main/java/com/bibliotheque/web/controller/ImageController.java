package com.bibliotheque.web.controller;

import com.bibliotheque.dto.ImageGalleryDTO;
import com.bibliotheque.model.ImageGallery;
import com.bibliotheque.service.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestBody ImageGalleryDTO imageGalleryDTO)
    {
        System.out.println("\n upload ");

        ImageGallery imageGallery = modelMapper.map(imageGalleryDTO, ImageGallery.class);

        ImageGallery imageSave = imageService.saveImage(imageGallery);

        ImageGalleryDTO imageDTO = modelMapper.map(imageSave, ImageGalleryDTO.class);

        return new ResponseEntity<ImageGalleryDTO>(imageDTO, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getImageById(@PathVariable(name = "id")Long id)
    {
        ImageGallery image = imageService.getImageByID(id);

        System.out.println("\n image " + image.toString());

        ImageGalleryDTO imageDTO = modelMapper.map(image, ImageGalleryDTO.class);

        return new ResponseEntity<ImageGalleryDTO>(imageDTO, HttpStatus.ACCEPTED);
    }

}
