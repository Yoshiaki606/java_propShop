package com.example.Final.service.Property.CommandPattern;

import com.example.Final.entity.listingservice.Properties;
import com.example.Final.repository.PropertyRepo;

public class DeletePropertyCommand implements ICommand{
    private final Properties property;

    private final PropertyRepo propertyRepo;

    public DeletePropertyCommand(Properties property, PropertyRepo propertyRepo){
        this.property = property;
        this.propertyRepo = propertyRepo;
    }

    public void execute(){
        propertyRepo.delete(property);
    }
}
