package com.example.Final;

import com.example.Final.entity.listingservice.Properties;
import com.example.Final.service.History.HistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HistoryServiceTest {

    private HistoryService historyService;
    private Object mockObjectService;

    @BeforeEach
    void setUp() {
        // Tạo subclass giả lập để kiểm tra các phương thức abstract
        historyService = Mockito.mock(HistoryService.class, CALLS_REAL_METHODS);
        mockObjectService = new Object();

        // Mock các phương thức abstract
        when(historyService.createObjectService()).thenReturn(mockObjectService);
    }

    @Test
    void testCreateHistory() {
        // Arrange
        Properties mockProperties = mock(Properties.class);

        // Act
        historyService.createHistory(mockProperties);

        // Assert: Xác minh rằng tất cả các phương thức abstract được gọi đúng cách
        verify(historyService).createObjectService();
        verify(historyService).setPriceObject(mockObjectService, mockProperties);
        verify(historyService).setDate(eq(mockObjectService), anyString());
        verify(historyService).setProperties(mockObjectService, mockProperties);
        verify(historyService).setStatus(mockObjectService);
        verify(historyService).setSource(mockObjectService);
        verify(historyService).saveToDB(mockObjectService);
    }

    @Test
    void testGetFormattedDate() throws Exception {
        // Arrange: Sử dụng Reflection để kiểm tra phương thức private
        var method = HistoryService.class.getDeclaredMethod("getFormattedDate");
        method.setAccessible(true);

        // Act
        String formattedDate = (String) method.invoke(historyService);

        // Assert
        String expectedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        assertEquals(expectedDate, formattedDate);
    }
}