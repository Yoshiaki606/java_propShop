package com.example.Final.controller;

import com.example.Final.service.Image.ImageUploadService;
import com.example.Final.service.Image.ValidationImageUploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ValidationImageUploadServiceTest {

    private ImageUploadService wrappedService;
    private ValidationImageUploadService validationService;

    @BeforeEach
    void setUp() {
        wrappedService = mock(ImageUploadService.class);
        validationService = new ValidationImageUploadService(wrappedService);
    }

    @Test
    void testUploadImageThrowsExceptionForEmptyFile() {
        MultipartFile emptyFile = mock(MultipartFile.class);
        when(emptyFile.isEmpty()).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> validationService.uploadImage(emptyFile));
        verifyNoInteractions(wrappedService);
    }

    @Test
    void testUploadImageDelegatesToWrappedService() throws Exception {
        MultipartFile validFile = mock(MultipartFile.class);
        when(validFile.isEmpty()).thenReturn(false);
        when(wrappedService.uploadImage(validFile)).thenReturn("http://example.com/image.jpg");

        String result = validationService.uploadImage(validFile);

        verify(wrappedService, times(1)).uploadImage(validFile);
        assert result.equals("http://example.com/image.jpg");
    }
}