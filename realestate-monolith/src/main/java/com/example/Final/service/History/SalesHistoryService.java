package com.example.Final.service.History;

import com.example.Final.entity.listingservice.Properties;
import com.example.Final.entity.rentalservice.RentalHistory;
import com.example.Final.entity.salesservice.SalesHistory;
import com.example.Final.repository.SalesHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class SalesHistoryService extends HistoryService{

    private final SalesHistoryRepo salesHistoryRepo;

    public SalesHistoryService(SalesHistoryRepo salesHistoryRepo) {
        this.salesHistoryRepo = salesHistoryRepo;
    }



    public Object createObjectService(){
        return new SalesHistory();
    }

    public void setPriceObject(Object object, Properties properties){
        ((SalesHistory)object).setPrice(properties.getPropertyPrice());
    }

   public void setDate(Object object, String formattedDate) {
        ((SalesHistory)object).setCreateDate(formattedDate);
    }

    public void setProperties(Object object, Properties properties){
        ((SalesHistory)object).setProperties(properties);
    }

    public void setStatus(Object object){
        ((SalesHistory)object).setStatus("Bán");
    }

    public void setSource(Object object){
        ((SalesHistory)object).setSource("Người môi giới cung cấp");
    }

    public void saveToDB(Object object){
        // fix
        salesHistoryRepo.save((SalesHistory) object);
    }
}
