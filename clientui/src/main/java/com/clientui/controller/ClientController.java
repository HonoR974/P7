package com.clientui.controller;

import com.clientui.model.ImageGallery;
import com.clientui.service.ImageGalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;


@Controller
public class ClientController {


    @Autowired
    private ImageGalleryService imageGalleryService;

    @GetMapping("/")
    public String accueil(Model model)
    {
        return "Accueil";
    }

    @GetMapping("/index")
    public String index(Model model)
    {
        ImageGallery img = new ImageGallery();
        model.addAttribute("img", img);
        return "index";
    }

    @PostMapping("/image/saveImageDetails")
    public String createProduct(@RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    Model model,
                                     @RequestParam("image") MultipartFile file)
            throws IOException, InterruptedException {

        System.out.println("\n createProduct ");

        if (file.isEmpty())
        {
            System.out.println("\n image vide ");
        }

            String[] names = name.split(",");
            String[] descriptions = description.split(",");

            byte[] imageData = file.getBytes();
            ImageGallery imageGallery = new ImageGallery();
            imageGallery.setName(names[0]);
            imageGallery.setImage(imageData);
            imageGallery.setDescription(descriptions[0]);


            model.addAttribute("img",   imageGalleryService.saveImage(imageGallery));
            return "image";
    }

    @GetMapping("/image/display/{id}")
    @ResponseBody
    void showImage(@PathVariable("id") Long id, HttpServletResponse response, Optional<ImageGallery> imageGallery)
            throws ServletException, IOException, InterruptedException {

        ImageGallery image = imageGalleryService.getImageByID(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(image.getImage());
        response.getOutputStream().close();
    }


}