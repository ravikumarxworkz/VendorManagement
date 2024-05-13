package com.xworkz.vendorManagementProject.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xworkz.vendorManagementProject.service.VendorService;

@RestController
@RequestMapping("/")
public class RegisterValidationcontroller {

	@Autowired
	private VendorService vendorService;

	@GetMapping(value = "/checkEmailExistence/{email}")
	public ResponseEntity<String> checkEmailExit(@PathVariable String email) {
		System.out.println("emaillllll============="+email);
		boolean emailExistsInDatabase = vendorService.checkEmailExistence(email);
		System.out.println("result============"+emailExistsInDatabase);
		if (emailExistsInDatabase) {
			return ResponseEntity.ok("EmailIDexists");
		} else {
			return ResponseEntity.ok("not exists");
		}
	}


	@GetMapping(value = "/checkGSTNumberExistence/{GSTNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> checkGSTNumberExistence(@PathVariable String GSTNumber) {
	    boolean exists = this.vendorService.checkGSTNumberExistence(GSTNumber);

	    if (exists) {
	        return ResponseEntity.ok("GSTNumberExist");
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

	@GetMapping(value = "/checkContactNumberExistence/{contactNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> checkContactNumberExistence(@PathVariable Long contactNumber) {
	    System.out.println("contactNumber============" + contactNumber);
	    boolean exists = this.vendorService.checkContactNumberExistence(contactNumber);

	    if (exists) {
	        return ResponseEntity.ok("contactNumberexists");
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

	@GetMapping(value = "/checkWebsiteExistence/{website}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> checkWebsiteExistence(@PathVariable String website) {
	    System.out.println("contactNumber============" + website);
	    boolean exists = this.vendorService.checkWebsiteExistence(website);

	    if (exists) {
	        return ResponseEntity.ok("websiteExists");
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

}
