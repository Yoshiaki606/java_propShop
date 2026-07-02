package com.example.Final.repository;

import com.example.Final.entity.listingservice.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {
}
