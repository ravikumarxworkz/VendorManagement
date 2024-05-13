package com.xworkz.vendorManagementProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xworkz.vendorManagementProject.entity.EmailValidationEntity;

@Repository
public interface EmailValidationRepository extends JpaRepository<EmailValidationEntity, Integer> {
    boolean existsByEmail(String email);
    
    List<EmailValidationEntity> findByEmail(String email);
    
       
}

