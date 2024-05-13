/**
 * 
 */
package com.xworkz.vendorManagementProject.service;

/**
 * 
 */
public interface EmailValidateService {

	boolean checkEmailExits(String email);
	
	boolean saveOTPByEmailId(String email);
	
	boolean validateOPT(String email, String otp);

}
