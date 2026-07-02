package com.example.Final;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.example.Final.service.Image.ImageUploadCloudinaryServiceAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ImageUploadCloudinaryServiceAdapterTest {

    private Cloudinary cloudinary;
    private Uploader uploader;
    private ImageUploadCloudinaryServiceAdapter adapter;

    @BeforeEach
    void setUp() {
        cloudinary = mock(Cloudinary.class);
        uploader = mock(Uploader.class);
        adapter = new ImageUploadCloudinaryServiceAdapter(cloudinary);

        // Mock the uploader behavior
        when(cloudinary.uploader()).thenReturn(uploader);
    }

    @Test
    void testUploadImage() throws IOException {
        // Mock MultipartFile
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenReturn("test-image-bytes".getBytes());

        // Mock Cloudinary upload response
        Map<String, String> uploadResult = Map.of("url", "http://example.com/test-image.jpg");
        when(uploader.upload(any(byte[].class), anyMap())).thenReturn(uploadResult);

        // Call the method
        String result = adapter.uploadImage(file);

        // Verify the result
        assertEquals("http://example.com/test-image.jpg", result);

        // Verify interactions
        verify(file, times(1)).getBytes();
        verify(uploader, times(1)).upload(any(byte[].class), anyMap());
    }
}
