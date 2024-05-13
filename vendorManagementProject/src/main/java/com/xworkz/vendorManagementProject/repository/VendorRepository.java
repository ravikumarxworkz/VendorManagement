package com.xworkz.vendorManagementProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xworkz.vendorManagementProject.entity.VendorEntity;

@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, Integer> {

	boolean existsByEmailId(String emailId);

	boolean existsByContactNumber(long contactNumber);

	boolean existsByWebsite(String website);

	boolean existsByGstNumber(String gstNumber);

}
