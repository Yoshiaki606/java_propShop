package com.example.Final.service.Image;

import org.springframework.web.multipart.MultipartFile;

public class ValidationImageUploadService extends  ImageUploadServiceDecorator {

    public ValidationImageUploadService(ImageUploadService wrapped) {
        super(wrapped);
    }

    @Override
    public String uploadImage(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty!");
        }
        return super.wrapped.uploadImage(file);
    }
}
