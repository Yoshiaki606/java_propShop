package com.example.Final.service.History;

import com.example.Final.entity.listingservice.Properties;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class HistoryService {
    public void createHistory(Properties properties){
        Object objectService =  createObjectService();
        String formattedDate = getFormattedDate();

        setPriceObject(objectService, properties);
        setDate(objectService, formattedDate);
        setProperties(objectService, properties);
        setStatus(objectService);
        setSource(objectService);
        saveToDB(objectService);
    }

    private String getFormattedDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return currentDate.format(formatter);
    }

    public abstract Object createObjectService();
    public abstract void setPriceObject(Object object, Properties properties);
    public abstract void setDate(Object object, String formattedDate);
    public abstract void setProperties(Object object, Properties properties);
    public abstract void setStatus(Object object);
    public abstract void setSource(Object object);
    public abstract void saveToDB(Object object);
}
