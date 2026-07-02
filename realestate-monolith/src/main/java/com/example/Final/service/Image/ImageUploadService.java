package com.example.Final.service.Image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    public String uploadImage(MultipartFile image) throws Exception;
}
