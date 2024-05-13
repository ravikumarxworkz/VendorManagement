package com.xworkz.vendorManagementProject.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xworkz.vendorManagementProject.dto.VendorDTO;
import com.xworkz.vendorManagementProject.entity.VendorEntity;
import com.xworkz.vendorManagementProject.repository.VendorRepository;
import com.xworkz.vendorManagementProject.violation.CustomConstraintViolation;

@Service
@Transactional
public class VendorServiceImpl implements VendorService {

	@Autowired
	private VendorRepository vendorRepository;

	private static final Logger logger = LoggerFactory.getLogger(VendorServiceImpl.class);

	@Override
	public boolean checkEmailExistence(String email) {
		logger.info("checkEmailExistence service" + email);
		return this.vendorRepository.existsByEmailId(email);
	}

	@Override
	public boolean checkContactNumberExistence(long contactNumber) {
		logger.info("checkContactNumberExistence service" + contactNumber);
		return this.vendorRepository.existsByContactNumber(contactNumber);
	}

	@Override
	public boolean checkGSTNumberExistence(String GSTnumber) {
		logger.info("checkGSTNumberExistence service" + GSTnumber);
		return this.vendorRepository.existsByGstNumber(GSTnumber);
	}

	@Override
	public boolean checkWebsiteExistence(String website) {
		logger.info("checkWebsiteExistence service" + website);
		return this.vendorRepository.existsByWebsite(website);
	}

	   @Override
	    public Set<ConstraintViolation<VendorDTO>> validateAndSaveVendorDTO(VendorDTO vendorDTO) {
		   logger.info("validate and save dto is entering {}", vendorDTO);
	        boolean checkEmailExistence = vendorRepository.existsByEmailId(vendorDTO.getEmailId());
	        boolean checkContactNumberExistence = vendorRepository.existsByContactNumber(vendorDTO.getContactNumber());
	        boolean checkWebsiteExistence = vendorRepository.existsByWebsite(vendorDTO.getWebsite());
	        boolean checkGSTNumberExistence = vendorRepository.existsByGstNumber(vendorDTO.getGstNumber());

	        Set<ConstraintViolation<VendorDTO>> constraintViolations = new HashSet<>();

	        if (checkEmailExistence) {
	            constraintViolations.add(new CustomConstraintViolation<>("email", "Email already exists"));
	            logger.info("Email already exists for {}", vendorDTO.getEmailId());
	        }

	        if (checkContactNumberExistence) {
	            constraintViolations.add(new CustomConstraintViolation<>("contactNumber", "Contact number already exists"));
	            logger.info("Contact number already exists for {}", vendorDTO.getContactNumber());
	        }

	        if (checkWebsiteExistence) {
	            constraintViolations.add(new CustomConstraintViolation<>("website", "Website already exists"));
	            logger.info("Website already exists for {}", vendorDTO.getWebsite());
	        }

	        if (checkGSTNumberExistence) {
	            constraintViolations.add(new CustomConstraintViolation<>("GSTNumber", "GSTNumber already exists"));
	            logger.info("GSTNumber already exists for {}", vendorDTO.getGstNumber());
	        }

	        Set<ConstraintViolation<VendorDTO>> dtoConstraintViolations = validate(vendorDTO);
	        constraintViolations.addAll(dtoConstraintViolations);

	        if (!constraintViolations.isEmpty()) {
	            return constraintViolations;
	        }

	        VendorEntity vendorEntity = new VendorEntity();
	        vendorDTO.setImagePath("defaultUserImage.png");
	        vendorDTO.setStatus("pending");
	        BeanUtils.copyProperties(vendorDTO, vendorEntity);

	        VendorEntity saveEntity = this.vendorRepository.save(vendorEntity);
	        if (saveEntity != null) {
	            logger.info("Vendor saved successfully: {}", saveEntity);
	            return Collections.emptySet();
	        } else {
	            logger.error("Failed to save vendor");
	            return Collections.emptySet(); // Not sure if this is the desired behavior
	        }
	    }

	private Set<ConstraintViolation<VendorDTO>> validate(VendorDTO DTO) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<VendorDTO>> violations = validator.validate(DTO);
		return violations;
	}

}
