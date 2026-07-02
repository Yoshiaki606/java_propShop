package com.example.Final.service.History;

import com.example.Final.entity.listingservice.Properties;
import com.example.Final.entity.rentalservice.RentalHistory;

import com.example.Final.entity.salesservice.SalesHistory;
import com.example.Final.repository.RentalHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class RentalHistoryService extends HistoryService{


    private final RentalHistoryRepo rentalHistoryRepo;

    public RentalHistoryService(RentalHistoryRepo rentalHistoryRepo) {
        this.rentalHistoryRepo = rentalHistoryRepo;
    }


    public Object createObjectService(){
        return new RentalHistory();
    }

    public void setPriceObject(Object object, Properties properties){
        ((RentalHistory)object).setRentalPrice(properties.getPropertyPrice());
    }

    public void setDate(Object object, String formattedDate) {
        ((RentalHistory)object).setCreatedAt(formattedDate);
    }

    public void setProperties(Object object, Properties properties){
        ((RentalHistory)object).setProperties(properties);
    }

    public void setStatus(Object object){
        ((RentalHistory)object).setStatus("Cho thuê");
    }

    public void setSource(Object object){
        ((RentalHistory)object).setSource("Người môi giới cung cấp");
    }

    public void saveToDB(Object object){
        // fix
        rentalHistoryRepo.save((RentalHistory)object);
    }
}
