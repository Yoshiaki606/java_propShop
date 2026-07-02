package com.example.Final.repository;

import com.example.Final.entity.listingservice.PostInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepo extends JpaRepository<PostInformation, Integer> {
}
