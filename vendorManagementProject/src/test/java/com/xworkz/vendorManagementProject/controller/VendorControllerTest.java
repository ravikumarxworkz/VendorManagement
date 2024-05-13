package com.xworkz.vendorManagementProject.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xworkz.vendorManagementProject.dto.VendorDTO;
import com.xworkz.vendorManagementProject.service.VendorService;
import com.xworkz.vendorManagementProject.violation.CustomConstraintViolation;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class VendorControllerTest {

	@Mock
	private VendorService vendorService;

	@InjectMocks
	private VendorController vendorController;

	@Mock
	private RedirectAttributes redirectAttributes;

	@Mock
	private Validator validator;

	@Mock
	private Logger logger;

	@Mock
	private HttpSession session;

	@Captor
	private ArgumentCaptor<String> stringArgumentCaptor;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(vendorController).build();
	}

	@Test
	public void testHomePage() throws Exception {
		mockMvc.perform(get("/homePage")).andExpect(status().isOk()).andExpect(view().name("index"));
	}

	@Test
	public void testLogInPage() throws Exception {
		mockMvc.perform(get("/logInPage")).andExpect(status().isOk()).andExpect(view().name("logIn"));
	}

	@Test
	public void testRegisterPage() throws Exception {
		mockMvc.perform(get("/registerPage")).andExpect(status().isOk()).andExpect(view().name("register"));
	}

	@Test
	public void testSaveVendorDTOSuccess() {
		// Prepare test data
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setId(1);
		vendorDTO.setOwnerName("Ravikumar");
		vendorDTO.setEmailId("ravikumar@gmail.com");
		vendorDTO.setContactNumber(8123179157l);
		vendorDTO.setAltContactNumber(9632974675l);
		vendorDTO.setVendorName("RaviShop");
		vendorDTO.setGstNumber("123456789123456");
		vendorDTO.setStartDate("10-10-2020");
		vendorDTO.setWebsite("www.xwok.in");
		vendorDTO.setAddress("bengaluru");
		vendorDTO.setPincode(591316);
		vendorDTO.setImagePath("defaultUserImage.png");
		// Fill in with appropriate data for success case

		// Define behavior of vendorServiceMock
		when(vendorService.validateAndSaveVendorDTO(vendorDTO)).thenReturn(Collections.emptySet());

		// Call the method under test
		String result = vendorController.saveVendorDTO(vendorDTO, session);

		// Verify behavior
		verify(session).setAttribute(eq("accountCreateMessage"), stringArgumentCaptor.capture());
		assertEquals(
				"Your account has been created successfully. However, your account is currently under verification. You won't be able to log in until it is verified.",
				stringArgumentCaptor.getValue());
		assertEquals("redirect:/logInPage", result);
	}

	@Test
	public void testSaveVendorDTOFailure() {
		// Prepare test data
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setId(1);
		vendorDTO.setOwnerName("Ravikumar");
		vendorDTO.setEmailId("ravikumar@gmail.com");
		vendorDTO.setContactNumber(81231l);
		vendorDTO.setAltContactNumber(963l);
		vendorDTO.setVendorName("RaviShop");
		vendorDTO.setGstNumber("123");
		vendorDTO.setStartDate("10-10-2020");
		vendorDTO.setWebsite("www.xwok.in");
		vendorDTO.setAddress("bengaluru");
		vendorDTO.setPincode(56);
		vendorDTO.setImagePath("defaultUserImage.png");
		Set<ConstraintViolation<VendorDTO>> constraintViolations = new HashSet<>();
		constraintViolations.add(new CustomConstraintViolation<>("property", "error message"));

		// Define behavior of vendorServiceMock
		when(vendorService.validateAndSaveVendorDTO(vendorDTO)).thenReturn(constraintViolations);

		// Call the method under test
		String result = vendorController.saveVendorDTO(vendorDTO, session);

		// Verify behavior
		verify(session).setAttribute(eq("errorMessage"), stringArgumentCaptor.capture());
		// Assuming you concatenate errors with "; "
		//assertEquals("property: error message; ", stringArgumentCaptor.getValue());
		assertEquals("redirect:/registerPage", result);
	}

}
