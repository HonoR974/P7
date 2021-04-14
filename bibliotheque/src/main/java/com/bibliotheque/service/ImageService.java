package com.bibliotheque.service;

import com.bibliotheque.model.ImageGallery;

import java.util.Optional;

public interface ImageService {

    ImageGallery saveImage(ImageGallery imageGallery);

    Optional<ImageGallery> getImageByID(Long id);

}
