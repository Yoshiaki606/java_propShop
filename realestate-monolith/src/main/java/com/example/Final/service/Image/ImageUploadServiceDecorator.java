package com.example.Final.service.Image;

import org.springframework.web.multipart.MultipartFile;

public abstract class ImageUploadServiceDecorator implements ImageUploadService{
    protected final ImageUploadService wrapped;

    public ImageUploadServiceDecorator(ImageUploadService wrapped){
        this.wrapped = wrapped;
    }
}
