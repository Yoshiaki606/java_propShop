package com.example.Final.service.Image;

import org.springframework.web.multipart.MultipartFile;

public class LoggingImageUploadService extends ImageUploadServiceDecorator{
    public LoggingImageUploadService(ImageUploadService wrapped) {
        super(wrapped);
    }

    @Override
    public String uploadImage(MultipartFile image) throws Exception {
        System.out.println("Image file name uploaded: " + image.getOriginalFilename());
        String imageURL = super.wrapped.uploadImage(image);
        System.out.println("Image uploaded to: " + imageURL);
        return imageURL;
    }
}
