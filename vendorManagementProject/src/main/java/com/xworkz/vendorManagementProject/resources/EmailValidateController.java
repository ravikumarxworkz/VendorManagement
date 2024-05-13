package com.xworkz.vendorManagementProject.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xworkz.vendorManagementProject.service.EmailValidateService;


@RestController
@RequestMapping("/")
public class EmailValidateController {

	
	@Autowired
	private EmailValidateService emailValidateService;

	/*
	 * @GetMapping(value = "/checkEmailExistence/{email}") public
	 * ResponseEntity<String> checkEmailExit(@PathVariable String email) {
	 * System.out.println("emaillllll============="+email); boolean
	 * emailExistsInDatabase = emailValidateService.checkEmailExits(email);
	 * System.out.println("result============"+emailExistsInDatabase); if
	 * (emailExistsInDatabase) { return ResponseEntity.ok("EmailIDexists"); } else {
	 * return ResponseEntity.ok("not exists"); } }
	 */
	@PostMapping("/genrateOTPAndSave")
	public ResponseEntity<String> genrateOTPAndSave(@RequestParam String email) {
		System.out.println("saveOTPByEmailId======"+email);
		boolean emailExistsInDatabase = emailValidateService.saveOTPByEmailId(email);
		if (emailExistsInDatabase) {
			return ResponseEntity.ok("Exists");
		} else {
			return ResponseEntity.ok("not exists");
		}
	}

	@PostMapping("/validateEmailVerificationOTP")
	public ResponseEntity<String> validateOTP(@RequestParam String email, @RequestParam String otp) {
		boolean validate = emailValidateService.validateOPT(email, otp);
		if (validate) {
			return ResponseEntity.ok("valid");
		} else {
			return ResponseEntity.ok("not valid");
		}
	}
}
