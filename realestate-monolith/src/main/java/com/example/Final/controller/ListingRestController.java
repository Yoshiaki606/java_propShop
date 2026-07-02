package com.example.Final.controller;

import com.example.Final.dto.AddressDTO;
import com.example.Final.dto.ListingDTO;
import com.example.Final.dto.PostInformationDTO;
import com.example.Final.entity.listingservice.Address;
import com.example.Final.entity.listingservice.PostInformation;
import com.example.Final.entity.listingservice.Properties;
import com.example.Final.service.Property.PropertyService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class ListingRestController {

    private final PropertyService propertyService;

    @GetMapping
    public ResponseEntity<List<ListingDTO>> getAllListings() {
        List<Properties> properties = propertyService.getAll();
        List<ListingDTO> dtos = properties.stream()
                .filter(Properties::isAvailable)
                .map(this::convertToListingDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingDTO> getListingById(@PathVariable("id") int id) {
        try {
            Properties property = propertyService.getById(id);
            return ResponseEntity.ok(convertToListingDTO(property));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ListingDTO> createListing(@RequestBody CreateListingRequest request) {
        Properties property = new Properties();
        property.setPropertyTitle(request.getPropertyTitle());
        property.setPropertyPrice(request.getPropertyPrice());
        property.setPropertyType(request.getPropertyType());
        property.setPropertyTypeTransaction(request.getPropertyTypeTransaction());
        property.setPropertyInterior(request.getPropertyInterior());
        property.setPropertyLegal(request.getPropertyLegal());
        property.setPropertyDescription(request.getPropertyDescription());
        property.setPropertyFloor(request.getPropertyFloor());
        property.setBedrooms(request.getBedrooms());
        property.setBathrooms(request.getBathrooms());
        property.setSquareMeters(request.getSquareMeters());
        property.setPropertyLongitude(request.getPropertyLongitude());
        property.setPropertyLatitude(request.getPropertyLatitude());
        property.setPropertyStatus("Chờ duyệt");
        property.setAvailable(true);

        Address address = new Address();
        address.setStreet(request.getStreet());
        address.setWard(request.getWard());
        address.setDistrict(request.getDistrict());
        address.setProvince(request.getProvince());
        address.setFullAddress(request.getFullAddress());
        address.setProperties(property);
        property.setAddress(address);

        PostInformation postInfo = new PostInformation();
        postInfo.setFullName(request.getContactName());
        postInfo.setEmail(request.getContactEmail());
        postInfo.setPhone(request.getContactPhone());
        postInfo.setDatePost(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        postInfo.setDaysRemaining(30);
        postInfo.setProperties(property);
        property.setPostInformation(postInfo);

        Properties saved = propertyService.create(property);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToListingDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable("id") int id) {
        try {
            propertyService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private ListingDTO convertToListingDTO(Properties property) {
        ListingDTO dto = new ListingDTO();
        dto.setPropertyId(property.getPropertyId());
        dto.setPropertyTitle(property.getPropertyTitle());
        dto.setPropertyPrice(property.getPropertyPrice());
        dto.setPropertyType(property.getPropertyType());
        dto.setPropertyTypeTransaction(property.getPropertyTypeTransaction());
        dto.setAvailable(property.isAvailable());
        dto.setPropertyInterior(property.getPropertyInterior());
        dto.setPropertyLegal(property.getPropertyLegal());
        dto.setPropertyDescription(property.getPropertyDescription());
        dto.setPropertyFloor(property.getPropertyFloor());
        dto.setPropertyStatus(property.getPropertyStatus());
        dto.setBedrooms(property.getBedrooms());
        dto.setBathrooms(property.getBathrooms());
        dto.setSquareMeters(property.getSquareMeters());
        dto.setPropertyPriority(property.getPropertyPriority());
        dto.setPropertyLongitude(property.getPropertyLongitude());
        dto.setPropertyLatitude(property.getPropertyLatitude());

        if (property.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setAddressId(property.getAddress().getAddressId());
            addressDTO.setStreet(property.getAddress().getStreet());
            addressDTO.setWard(property.getAddress().getWard());
            addressDTO.setDistrict(property.getAddress().getDistrict());
            addressDTO.setProvince(property.getAddress().getProvince());
            addressDTO.setFullAddress(property.getAddress().getFullAddress());
            dto.setAddress(addressDTO);
        }

        if (property.getPostInformation() != null) {
            PostInformationDTO postDTO = new PostInformationDTO();
            postDTO.setContactId(property.getPostInformation().getContactId());
            postDTO.setFullName(property.getPostInformation().getFullName());
            postDTO.setEmail(property.getPostInformation().getEmail());
            postDTO.setPhone(property.getPostInformation().getPhone());
            postDTO.setDatePost(property.getPostInformation().getDatePost());
            postDTO.setDateEnd(property.getPostInformation().getDateEnd());
            postDTO.setTypePost(property.getPostInformation().getTypePost());
            postDTO.setDaysRemaining(property.getPostInformation().getDaysRemaining());
            postDTO.setPayment(property.getPostInformation().getPayment());
            postDTO.setPayPerDay(property.getPostInformation().getPayPerDay());
            dto.setPostInformation(postDTO);
        }

        if (property.getListImages() != null) {
            List<String> urls = property.getListImages().stream()
                    .map(com.example.Final.entity.listingservice.Images::getImageUrl)
                    .collect(Collectors.toList());
            dto.setImageUrls(urls);
        }

        return dto;
    }

    @Data
    public static class CreateListingRequest {
        private String propertyTitle;
        private double propertyPrice;
        private String propertyType;
        private String propertyTypeTransaction;
        private String propertyInterior;
        private String propertyLegal;
        private String propertyDescription;
        private int propertyFloor;
        private int bedrooms;
        private int bathrooms;
        private double squareMeters;
        private Double propertyLongitude;
        private Double propertyLatitude;
        
        private String street;
        private String ward;
        private String district;
        private String province;
        private String fullAddress;
        
        private String contactName;
        private String contactEmail;
        private String contactPhone;
    }
}
