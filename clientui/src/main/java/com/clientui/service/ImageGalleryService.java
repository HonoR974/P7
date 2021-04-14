package com.clientui.service;

import com.clientui.model.ImageGallery;

import java.io.IOException;

public interface ImageGalleryService {

    ImageGallery saveImage(ImageGallery imageGallery) throws IOException, InterruptedException;

    ImageGallery getImageByID(Long id) throws IOException, InterruptedException;
}
