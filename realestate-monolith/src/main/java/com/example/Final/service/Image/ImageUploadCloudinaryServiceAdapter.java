package com.example.Final.service.Image;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageUploadCloudinaryServiceAdapter implements ImageUploadService{
    private static ImageUploadCloudinaryServiceAdapter imageUploadInstance;

    private final Cloudinary cloudinary;

    public ImageUploadCloudinaryServiceAdapter(Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        try{

            Map data = cloudinary.uploader().upload(file.getBytes(), Map.of());

            return (String) data.get("url");
        }
        catch (IOException e){
            throw new RuntimeException("Image uploading fail");
        }
    }
}
