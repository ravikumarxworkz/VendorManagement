package com.xworkz.vendorManagementProject.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xworkz.vendorManagementProject.entity.EmailValidationEntity;
import com.xworkz.vendorManagementProject.repository.EmailValidationRepository;

@Service
@Transactional
public class EmailValidateServiceImpl implements EmailValidateService {

	@Autowired
	private EmailValidationRepository emailValidationRepository;
	
	

	@Override
	public boolean checkEmailExits(String email) {
		return emailValidationRepository.existsByEmail(email);

	}

	@Override
	public boolean saveOTPByEmailId(String email) {
		if (email != null) {
			Random random = new Random();
			int generatedOtp = random.nextInt(900000) + 100000;
			String OTP = String.valueOf(generatedOtp);
			EmailValidationEntity entity = new EmailValidationEntity();
			entity.setEmail(email);
			entity.setOTP(OTP);
			entity.setOTPCreatedTime(LocalDateTime.now());
           System.out.println("+++++++++++++++++++"+entity);
			EmailValidationEntity saveOTP = emailValidationRepository.save(entity);
			if (saveOTP != null) {
				// boolean sent = mailSending.sendEmailVerficationOTP(email, OTP);
				// if (sent) {
				return true;
				// }

			}
		}

		return false;
	}

	@Override
	public boolean validateOPT(String email, String otp) {
		if (email != null && otp != null) {
			List<EmailValidationEntity> latestotp = emailValidationRepository.findByEmail(email);
			if (latestotp != null) {
				for (EmailValidationEntity emailValidationEntity : latestotp) {
					if (emailValidationEntity.getOTP().equalsIgnoreCase(otp) ){
						emailValidationRepository.deleteById(emailValidationEntity.getId());
						return true;
					}
				}
			}

		}

		return false;
	}
}
