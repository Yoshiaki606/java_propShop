package com.example.Final.controller;

import com.example.Final.service.Image.ImageUploadService;
import com.example.Final.service.Image.LoggingImageUploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.*;

class LoggingImageUploadServiceTest {

    private ImageUploadService wrappedService;
    private LoggingImageUploadService loggingService;

    @BeforeEach
    void setUp() {
        wrappedService = mock(ImageUploadService.class);
        loggingService = new LoggingImageUploadService(wrappedService);
    }

    @Test
    void testUploadImageLogsAndDelegates() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test-image.jpg");
        when(wrappedService.uploadImage(file)).thenReturn("http://example.com/image.jpg");

        String result = loggingService.uploadImage(file);

        verify(wrappedService, times(1)).uploadImage(file);
        assert result.equals("http://example.com/image.jpg");
    }
}