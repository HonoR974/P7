package com.bibliotheque.dto;

import lombok.Data;

@Data
public class ImageGalleryDTO {

    private Long id;

    private String name;

    private String description;

    private byte[] image;

}
