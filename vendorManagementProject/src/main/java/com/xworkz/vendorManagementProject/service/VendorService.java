package com.xworkz.vendorManagementProject.service;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.xworkz.vendorManagementProject.dto.VendorDTO;


public interface VendorService {
	
	boolean checkEmailExistence(String email);

	boolean checkContactNumberExistence(long contactNumber);

	boolean checkGSTNumberExistence(String GSTnumber);

	boolean checkWebsiteExistence(String website);
	
	Set<ConstraintViolation<VendorDTO>> validateAndSaveVendorDTO(VendorDTO vendorDTO);

}
