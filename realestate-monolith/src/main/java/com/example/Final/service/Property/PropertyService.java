package com.example.Final.service.Property;

import com.example.Final.entity.listingservice.HistoryListing;
import com.example.Final.entity.listingservice.Properties;
import com.example.Final.entity.securityservice.User;
import com.example.Final.repository.ContactRepo;
import com.example.Final.repository.PropertyRepo;
import com.example.Final.repository.RentalHistoryRepo;
import com.example.Final.repository.SalesHistoryRepo;
import com.example.Final.service.History.RentalHistoryService;
import com.example.Final.service.History.SalesHistoryService;
import com.example.Final.service.Property.CommandPattern.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyService {
    private final PropertyRepo propertyRepo;
    private final ContactRepo contactRepo;
    private final RentalHistoryService rentalHistoryService;
    private final SalesHistoryService salesHistoryService;

    private CommandInvoker commandInvoker = new CommandInvoker();

    private final RentalHistoryRepo rentalHistoryRepo;

    private final SalesHistoryRepo salesHistoryRepo;

    public PropertyService(PropertyRepo propertyRepo, ContactRepo contactRepo, 
                           RentalHistoryService rentalHistoryService, SalesHistoryService salesHistoryService,
                           RentalHistoryRepo rentalHistoryRepo, SalesHistoryRepo salesHistoryRepo) {
        this.propertyRepo = propertyRepo;
        this.contactRepo = contactRepo;
        this.rentalHistoryService = rentalHistoryService;
        this.salesHistoryService = salesHistoryService;
        this.rentalHistoryRepo = rentalHistoryRepo;
        this.salesHistoryRepo = salesHistoryRepo;
    }


    public List<Properties> getAll() {
        return propertyRepo.findAllByOrderByPropertyPriorityAsc();
    }

    public List<Properties> getAllByUser(User user) {
        return propertyRepo.getPropertiesByUser(user);
    }


    public List<Properties> getByHistoryListing(HistoryListing historyListing) {
        return propertyRepo.getPropertiesByHistoryListing(historyListing);
    }

    public Properties create(Properties properties) {
        return propertyRepo.save(properties);
    }
    // Save property when sold or rented using Command pattern
    public void save(Properties properties) {
        commandInvoker.addCommand(new CreatePropertyCommand(properties, propertyRepo));
        commandInvoker.executeCommand();
    }

    public void deleteById(int id) {
        Properties properties = propertyRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found property"));
        properties.setAvailable(false);
        propertyRepo.save(properties);
    }
    // Delete property when sold or rented using Command pattern

    public void delete(Properties properties) {
        commandInvoker.addCommand(new DeletePropertyCommand(properties, propertyRepo));
        commandInvoker.executeCommand();
    }

    public Properties getById(int id) {
        return propertyRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found property"));
    }

    public void updateInfo(int id, String type, String legal, String interior, double square, double price, int floor, int bed, int bath) {
        Properties properties = propertyRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found property"));
        properties.setPropertyType(type);
        properties.setPropertyLegal(legal);
        properties.setPropertyInterior(interior);
        properties.setBathrooms(bath);
        properties.setBedrooms(bed);
        properties.setPropertyFloor(floor);
        properties.setPropertyPrice(price);
        properties.setSquareMeters(square);
        propertyRepo.save(properties);
    }

    public void updateImages(Properties properties) {
        Properties oldProperties = propertyRepo.findById(properties.getPropertyId()).orElseThrow();
        oldProperties.setListImages(properties.getListImages());
        propertyRepo.save(oldProperties);
    }

    // Update property when sold or rented using Command pattern
    public void updateProperty(Properties properties) {
        commandInvoker.addCommand(new UpdatePropertyCommand(properties, propertyRepo, rentalHistoryRepo, salesHistoryRepo, contactRepo));
        commandInvoker.executeCommand();
    }

    public List<Properties> findPropertiesByKey(String key) {
        return propertyRepo.findPropertiesByKey(key);
    }


    public List<Properties> findPropertiesByProvince(String province) {
        return propertyRepo.findByAddress_Province(province);
    }

    public List<Properties> findByCity(String city, String houseType, Double priceMin,
                                       Double priceMax, Double sqmtMin,
                                       Double sqmtMax, Integer bedroom) {
        return propertyRepo.findByCity(city, houseType, priceMin, priceMax, sqmtMin, sqmtMax, bedroom);
    }

    public List<Properties> findPropertiesByForm(String optionType, String city,
                                                 String district, String ward,
                                                 Integer houseType, Integer priceRange,
                                                 Integer sqrtRange) {
        List<Properties> propertiesList = propertyRepo.findByAddress(city, district, ward);
        propertiesList = filterByType(propertiesList, houseType);
        propertiesList = filterPrice(propertiesList, priceRange);
        propertiesList = filterSqrt(propertiesList, sqrtRange);
        return propertiesList;
    }


    public List<Properties> filterByType(List<Properties> properties, Integer option) {
        if (option == null) {
            return properties;
        }
        List<Properties> filterProperties = new ArrayList<>();
        switch (option) {
            case 1:
                filterProperties = properties;
                break;
            case 2:
                for (Properties p : properties) {
                    if (p.getPropertyType().equals("Căn hộ")) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 3:
                for (Properties p : properties) {
                    if (p.getPropertyType().equals("Nhà riêng")) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 4:
                for (Properties p : properties) {
                    if (p.getPropertyType().equals("Biệt thự")) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 5:
                for (Properties p : properties) {
                    if (p.getPropertyType().equals("Nhà mặt phố")) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 6:
                for (Properties p : properties) {
                    if (p.getPropertyType().equals("Nhà trọ")) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 7:
                for (Properties p : properties) {
                    if (p.getPropertyType().equals("Đất nền")) {
                        filterProperties.add(p);
                    }
                }
                break;
            default:
                break;
        }
        return filterProperties;
    }

    public List<Properties> filterPrice(List<Properties> properties, Integer option) {
        if (option == null) {
            return properties;
        }
        List<Properties> filterProperties = new ArrayList<>();
        switch (option) {
            case 1:
                filterProperties = properties;
                break;
            case 2:
                for (Properties p : properties) {
                    if (p.getPropertyPrice() <= 500000000.0) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 3:
                for (Properties p : properties) {
                    if (p.getPropertyPrice() > 500000000.0 && p.getPropertyPrice() <= 800000000.0) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 4:
                for (Properties p : properties) {
                    if (p.getPropertyPrice() > 800000000.0 && p.getPropertyPrice() <= 1000000000.0) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 5:
                for (Properties p : properties) {
                    if (p.getPropertyPrice() > 1000000000.0 && p.getPropertyPrice() <= 2000000000.0) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 6:
                for (Properties p : properties) {
                    if (p.getPropertyPrice() > 2000000000.0 && p.getPropertyPrice() <= 3000000000.0) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 7:
                for (Properties p : properties) {
                    if (p.getPropertyPrice() > 3000000000.0 && p.getPropertyPrice() <= 5000000000.0) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 8:
                for (Properties p : properties) {
                    if (p.getPropertyPrice() > 5000000000.0 && p.getPropertyPrice() <= 7000000000.0) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 9:
                for (Properties p : properties) {
                    if (p.getPropertyPrice() > 7000000000.0 && p.getPropertyPrice() <= 10000000000.0) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 10:
                for (Properties p : properties) {
                    if (p.getPropertyPrice() > 10000000000.0 && p.getPropertyPrice() <= 20000000000.0) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 11:
                for (Properties p : properties) {
                    if (p.getPropertyPrice() > 20000000000.0 && p.getPropertyPrice() <= 30000000000.0) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 12:
                for (Properties p : properties) {
                    if (p.getPropertyPrice() > 30000000000.0 && p.getPropertyPrice() <= 40000000000.0) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 13:
                for (Properties p : properties) {
                    if (p.getPropertyPrice() > 40000000000.0) {
                        filterProperties.add(p);
                    }
                }
                break;
            default:
                break;
        }
        return filterProperties;
    }

    public List<Properties> filterSqrt(List<Properties> properties, Integer option) {
        if (option == null) {
            return properties;
        }
        List<Properties> filterProperties = new ArrayList<>();
        switch (option) {
            case 1:
                filterProperties = properties;
                break;
            case 2:
                for (Properties p : properties) {
                    if (p.getSquareMeters() <= 30) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 3:
                for (Properties p : properties) {
                    if (p.getSquareMeters() > 30 && p.getSquareMeters() <= 50) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 4:
                for (Properties p : properties) {
                    if (p.getSquareMeters() > 50 && p.getSquareMeters() <= 80) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 5:
                for (Properties p : properties) {
                    if (p.getSquareMeters() > 80 && p.getSquareMeters() <= 100) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 6:
                for (Properties p : properties) {
                    if (p.getSquareMeters() > 100 && p.getSquareMeters() <= 150) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 7:
                for (Properties p : properties) {
                    if (p.getSquareMeters() > 150 && p.getSquareMeters() <= 200) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 8:
                for (Properties p : properties) {
                    if (p.getSquareMeters() > 200 && p.getSquareMeters() <= 250) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 9:
                for (Properties p : properties) {
                    if (p.getSquareMeters() > 250 && p.getSquareMeters() <= 300) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 10:
                for (Properties p : properties) {
                    if (p.getSquareMeters() > 300 && p.getSquareMeters() <= 500) {
                        filterProperties.add(p);
                    }
                }
                break;
            case 11:
                for (Properties p : properties) {
                    if (p.getSquareMeters() > 500) {
                        filterProperties.add(p);
                    }
                }
                break;
            default:
                break;
        }
        return filterProperties;
    }

    public List<Properties> sortPriceByCityASC(String city) {
        return propertyRepo.sortPriceByCityASC(city);
    }

    public List<Properties> sortPriceByCityDESC(String city) {
        return propertyRepo.sortPriceByCityDESC(city);
    }

    public List<Properties> sortPriceByAllDESC() {
        return propertyRepo.getPropertiesByOrderByPropertyPriceDescPropertyPriorityAsc();
    }

    public List<Properties> sortSqftByAllDESC() {
        return propertyRepo.getPropertiesByOrderBySquareMetersDescPropertyPriorityAsc();
    }

    public List<Properties> sortSqftByAllASC() {
        return propertyRepo.getPropertiesByOrderBySquareMetersAscPropertyPriorityAsc();
    }

    public List<Properties> sortPriceByAllASC() {
        return propertyRepo.getPropertiesByOrderByPropertyPriceAscPropertyPriorityAsc();
    }

    public List<Properties> sortSqftByCityASC(String city) {
        return propertyRepo.sortSqftByCityASC(city);
    }

    public List<Properties> sortSqftByCityDESC(String city) {
        return propertyRepo.sortSqftByCityDESC(city);
    }

    public List<Properties> sortSqftByAllDESC(String city) {
        return propertyRepo.sortSqftByCityDESC(city);
    }

    public List<Properties> caseGetAll(int option) {
        switch (option) {
            case 1:
                return propertyRepo.findAll();
            case 2:
                return propertyRepo.findByIsAvailable(true);
            case 3:
                return propertyRepo.findByPropertyStatus("Chờ duyệt");
            case 4:
                return propertyRepo.findByPropertyStatus("Từ chối");
            default:
                return propertyRepo.findAll();
        }
    }
}
