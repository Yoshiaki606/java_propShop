package com.example.Final.service.Property.CommandPattern;

import com.example.Final.entity.listingservice.PostInformation;
import com.example.Final.entity.listingservice.Properties;
import com.example.Final.repository.ContactRepo;
import com.example.Final.repository.PropertyRepo;
import com.example.Final.repository.RentalHistoryRepo;
import com.example.Final.repository.SalesHistoryRepo;
import com.example.Final.service.History.HistoryService;
import com.example.Final.service.History.RentalHistoryService;
import com.example.Final.service.History.SalesHistoryService;

public class UpdatePropertyCommand implements ICommand{

    private final Properties property;

    private final PropertyRepo propertyRepo;

    private final RentalHistoryRepo rentalHistoryRepo;

    private final SalesHistoryRepo salesHistoryRepo;

    private final ContactRepo contactRepo;

    public UpdatePropertyCommand(Properties property, PropertyRepo propertyRepo, RentalHistoryRepo rentalHistoryRepo, SalesHistoryRepo salesHistoryRepo, ContactRepo contactRepo){
        this.property = property;
        this.propertyRepo = propertyRepo;
        this.rentalHistoryRepo = rentalHistoryRepo;
        this.salesHistoryRepo = salesHistoryRepo;
        this.contactRepo = contactRepo;
    }

    // Update property when sold or rented or update information using template method
    public void execute(){
        Properties oldProperties = propertyRepo.findById(property.getPropertyId()).orElseThrow(() -> new RuntimeException("Property not found"));
        oldProperties.setPropertyTypeTransaction(property.getPropertyTypeTransaction());
        oldProperties.setPropertyTitle(property.getPropertyTitle());
        oldProperties.setPropertyDescription(property.getPropertyDescription());
        oldProperties.setSquareMeters(property.getSquareMeters());
        oldProperties.setPropertyFloor(property.getPropertyFloor());
        oldProperties.setBedrooms(property.getBedrooms());
        oldProperties.setBathrooms(property.getBathrooms());
        PostInformation postInformation = oldProperties.getPostInformation();

        if (property.getPostInformation() != null) {
            postInformation.setEmail(property.getPostInformation().getEmail());
            postInformation.setFullName(property.getPostInformation().getFullName());
            postInformation.setPhone(property.getPostInformation().getPhone());
            contactRepo.save(postInformation);
        }

        if (property.getPropertyPrice() != oldProperties.getPropertyPrice()) {
            // Ham khoi tao temple method
            HistoryService historyService;
            if (property.getPropertyTypeTransaction().equals(oldProperties.getPropertyTypeTransaction()) && oldProperties.getPropertyTypeTransaction().equals("rent")) {
                historyService = new RentalHistoryService(rentalHistoryRepo);
                historyService.createHistory(property);

                oldProperties.setPropertyPrice(property.getPropertyPrice());
            }

            if (property.getPropertyTypeTransaction().equals(oldProperties.getPropertyTypeTransaction()) && oldProperties.getPropertyTypeTransaction().equals("sell")) {
                historyService = new SalesHistoryService(salesHistoryRepo);
                historyService.createHistory(property);
                oldProperties.setPropertyPrice(property.getPropertyPrice());
            }
        }
        oldProperties.setPostInformation(postInformation);
        propertyRepo.save(oldProperties);
    }

}
