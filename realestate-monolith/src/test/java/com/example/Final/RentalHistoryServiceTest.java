package com.example.Final;

import com.example.Final.entity.listingservice.Properties;
import com.example.Final.entity.rentalservice.RentalHistory;
import com.example.Final.repository.RentalHistoryRepo;
import com.example.Final.service.History.RentalHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RentalHistoryServiceTest {

    private RentalHistoryRepo rentalHistoryRepo;
    private RentalHistoryService rentalHistoryService;

    @BeforeEach
    void setUp() {
        rentalHistoryRepo = mock(RentalHistoryRepo.class);
        rentalHistoryService = new RentalHistoryService(rentalHistoryRepo);
    }

    @Test
    void testCreateHistory() {
        // Arrange
        Properties properties = mock(Properties.class);
        when(properties.getPropertyPrice()).thenReturn(1500.0); // Giả lập giá trị propertyPrice

        // Act
        rentalHistoryService.createHistory(properties);

        // Assert: Capture giá trị của RentalHistory được lưu vào DB
        ArgumentCaptor<RentalHistory> captor = ArgumentCaptor.forClass(RentalHistory.class);
        verify(rentalHistoryRepo).save(captor.capture());

        RentalHistory savedRentalHistory = captor.getValue();
        assertEquals(1500.0, savedRentalHistory.getRentalPrice());
        assertEquals("Cho thuê", savedRentalHistory.getStatus());
        assertEquals("Người môi giới cung cấp", savedRentalHistory.getSource());
    }

    @Test
    void testCreateObjectService() {
        // Act
        Object obj = rentalHistoryService.createObjectService();

        // Assert
        assertEquals(RentalHistory.class, obj.getClass());
    }

    @Test
    void testSetPriceObject() {
        // Arrange
        RentalHistory rentalHistory = new RentalHistory();
        Properties properties = mock(Properties.class);
        when(properties.getPropertyPrice()).thenReturn(2000.0);

        // Act
        rentalHistoryService.setPriceObject(rentalHistory, properties);

        // Assert
        assertEquals(2000.0, rentalHistory.getRentalPrice());
    }

    @Test
    void testSetDate() {
        // Arrange
        RentalHistory rentalHistory = new RentalHistory();
        String formattedDate = "05/03/2025";

        // Act
        rentalHistoryService.setDate(rentalHistory, formattedDate);

        // Assert
        assertEquals("05/03/2025", rentalHistory.getCreatedAt());
    }

    @Test
    void testSetProperties() {
        // Arrange
        RentalHistory rentalHistory = new RentalHistory();
        Properties properties = new Properties();

        // Act
        rentalHistoryService.setProperties(rentalHistory, properties);

        // Assert
        assertEquals(properties, rentalHistory.getProperties());
    }

    @Test
    void testSetStatus() {
        // Arrange
        RentalHistory rentalHistory = new RentalHistory();

        // Act
        rentalHistoryService.setStatus(rentalHistory);

        // Assert
        assertEquals("Cho thuê", rentalHistory.getStatus());
    }

    @Test
    void testSetSource() {
        // Arrange
        RentalHistory rentalHistory = new RentalHistory();

        // Act
        rentalHistoryService.setSource(rentalHistory);

        // Assert
        assertEquals("Người môi giới cung cấp", rentalHistory.getSource());
    }
}