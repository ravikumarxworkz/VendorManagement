package com.xworkz.vendorManagementProject.controller;

import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xworkz.vendorManagementProject.dto.VendorDTO;
import com.xworkz.vendorManagementProject.service.VendorService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/")
public class VendorController {

	@Autowired
	private VendorService vendorService;

	private static final Logger logger = LoggerFactory.getLogger(VendorController.class);

	@GetMapping("/homePage")
	public String homePage() {
		return "index";

	}

	@GetMapping("/logInPage")
	public String logInPage() {
		return "logIn";

	}

	@GetMapping("/registerPage")
	public String registerPage() {
		return "register";

	}

	@PostMapping(value = "/createAccount", consumes = "application/x-www-form-urlencoded")
	public String saveVendorDTO(@Valid VendorDTO dto, HttpSession session) {
		logger.debug("Received request to save vendor DTO: {}", dto);
		Set<ConstraintViolation<VendorDTO>> constraintViolations = vendorService.validateAndSaveVendorDTO(dto);
		if (constraintViolations.isEmpty()) {
			session.setAttribute("accountCreateMessage",
					"Your account has been created successfully. However, your account is currently under verification. You won't be able to log in until it is verified.");
			return "redirect:/logInPage";
		} else {
			StringBuilder errorMessage = new StringBuilder();
			for (ConstraintViolation<VendorDTO> violation : constraintViolations) {
				errorMessage.append(violation.getPropertyPath()).append(": ").append(violation.getMessage())
						.append("; ");
			}
			logger.warn("Validation failed for vendor DTO: {}. Errors: {}", dto, errorMessage.toString());
			session.setAttribute("errorMessage", errorMessage.toString());
			return "redirect:/registerPage";
		}
	}

}
