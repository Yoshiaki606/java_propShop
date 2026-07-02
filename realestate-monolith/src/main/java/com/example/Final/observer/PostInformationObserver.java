package com.example.Final.observer;

import com.example.Final.entity.listingservice.Properties;
import com.example.Final.repository.ContactRepo;
import org.springframework.stereotype.Component;

@Component
public class PostInformationObserver implements PropertyObserver {

    private final ContactRepo contactRepo;

    public PostInformationObserver(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    @Override
    public void update(Properties property) {
        if (property.getPostInformation() != null) {
            contactRepo.save(property.getPostInformation());
        }
    }
}
