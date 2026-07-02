package com.example.Final;

import com.example.Final.entity.listingservice.Properties;
import com.example.Final.entity.salesservice.SalesHistory;
import com.example.Final.repository.SalesHistoryRepo;
import com.example.Final.service.History.SalesHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SalesHistoryServiceTest {

    private SalesHistoryRepo salesHistoryRepo;
    private SalesHistoryService salesHistoryService;

    @BeforeEach
    void setUp() {
        salesHistoryRepo = mock(SalesHistoryRepo.class);
        salesHistoryService = new SalesHistoryService(salesHistoryRepo);
    }

    @Test
    void testCreateHistory() {
        // Arrange
        Properties properties = mock(Properties.class);
        when(properties.getPropertyPrice()).thenReturn(500000.0); // Giả lập giá trị price

        // Act
        salesHistoryService.createHistory(properties);

        // Assert: Capture giá trị của SalesHistory được lưu vào DB
        ArgumentCaptor<SalesHistory> captor = ArgumentCaptor.forClass(SalesHistory.class);
        verify(salesHistoryRepo).save(captor.capture());

        SalesHistory savedSalesHistory = captor.getValue();
        assertEquals(500000.0, savedSalesHistory.getPrice());
        assertEquals("Bán", savedSalesHistory.getStatus());
        assertEquals("Người môi giới cung cấp", savedSalesHistory.getSource());
    }

    @Test
    void testCreateObjectService() {
        // Act
        Object obj = salesHistoryService.createObjectService();

        // Assert
        assertEquals(SalesHistory.class, obj.getClass());
    }

    @Test
    void testSetPriceObject() {
        // Arrange
        SalesHistory salesHistory = new SalesHistory();
        Properties properties = mock(Properties.class);
        when(properties.getPropertyPrice()).thenReturn(750000.0);

        // Act
        salesHistoryService.setPriceObject(salesHistory, properties);

        // Assert
        assertEquals(750000.0, salesHistory.getPrice());
    }

    @Test
    void testSetDate() {
        // Arrange
        SalesHistory salesHistory = new SalesHistory();
        String formattedDate = "05/03/2025";

        // Act
        salesHistoryService.setDate(salesHistory, formattedDate);

        // Assert
        assertEquals("05/03/2025", salesHistory.getCreateDate());
    }

    @Test
    void testSetProperties() {
        // Arrange
        SalesHistory salesHistory = new SalesHistory();
        Properties properties = new Properties();

        // Act
        salesHistoryService.setProperties(salesHistory, properties);

        // Assert
        assertEquals(properties, salesHistory.getProperties());
    }

    @Test
    void testSetStatus() {
        // Arrange
        SalesHistory salesHistory = new SalesHistory();

        // Act
        salesHistoryService.setStatus(salesHistory);

        // Assert
        assertEquals("Bán", salesHistory.getStatus());
    }

    @Test
    void testSetSource() {
        // Arrange
        SalesHistory salesHistory = new SalesHistory();

        // Act
        salesHistoryService.setSource(salesHistory);

        // Assert
        assertEquals("Người môi giới cung cấp", salesHistory.getSource());
    }
}
