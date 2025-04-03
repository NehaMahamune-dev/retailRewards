/**
 * 
 */
package com.retailer.rewardProgram.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.retailer.rewardProgram.dto.CustomerRequest;
import com.retailer.rewardProgram.dto.CustomerRewardsResponse;
import com.retailer.rewardProgram.model.Customer;
import com.retailer.rewardProgram.model.Transactions;
import com.retailer.rewardProgram.service.CustomerService;
import com.retailer.rewardProgram.service.RewardService;

/**
 * Test class for RewardController
 * 
 */
public class RewardControllerTest {

	@Mock
	private RewardService rewardService;

	@Mock
	private CustomerService customerService;

	@InjectMocks
	private RewardController rewardController;

	private Customer customer;
	private List<Transactions> transactions;
	Map<String, Integer> rewards;

	// All the sample data
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		transactions = List.of(new Transactions(1L, null, new BigDecimal("120"), LocalDate.now().minusMonths(1)),
				new Transactions(2L, null, new BigDecimal("150"), LocalDate.now().minusMonths(2)));

		rewards = Map.of("March", 150, "April", 90, "Total", 240);
		customer = new Customer("1234567890", "Ashley Graham", transactions);
		transactions.forEach(t -> t.setCustomer(customer));
	}

	// Positive Scenario - to fetch the rewards based on phoneNumer
	@Test
	public void getRewardsByCustomerIdTest_Success() {
		when(rewardService.getRewards("1234567890")).thenReturn(rewards);

		Map<String, Integer> response = rewardController.getRewardsByCustomerId("1234567890");
		assertNotNull(response);
		assertEquals(150, response.get("March"));
		assertEquals(90, response.get("April"));
		assertEquals(240, response.get("Total"));
	}

	// Negative Scenario - to fetch the rewards based on phoneNumer but phoneNumebr
	// not found
	@Test
	public void getRewardsByCustomerIdTest_CustomerNotFound() {
		when(rewardService.getRewards("9090908888")).thenReturn(Map.of());

		Map<String, Integer> response = rewardController.getRewardsByCustomerId("9090908888");
		assertNotNull(response);
		assertTrue(response.isEmpty());
	}

	// Positive Scenario - to fetch the customer data based on phoneNumer
	@Test
	public void testGetCustomerData_Success() {

		when(customerService.getCustomerData("1234567890")).thenReturn(customer);
		when(customerService.getTransactionByPhone("1234567890")).thenReturn(transactions);
		when(rewardService.getRewards("1234567890")).thenReturn(rewards);

		CustomerRewardsResponse response = rewardController.getCustomerData("1234567890");

		assertNotNull(response);
		assertEquals(customer, response.getCustomer());
		assertEquals(transactions, response.getTransactions());
		assertEquals(rewards, response.getRewardPoints());
	}

	// Negative Scenario - to fetch the customer data based on phoneNumer, where
	// data is not present
	@Test
	public void testGetCustomerData_Negative() {
		String phoneNumber = "1234567890";

		when(customerService.getCustomerData(phoneNumber)).thenReturn(null);
		when(customerService.getTransactionByPhone(phoneNumber)).thenReturn(Collections.emptyList());
		when(rewardService.getRewards(phoneNumber)).thenReturn(Collections.emptyMap());

		CustomerRewardsResponse response = rewardController.getCustomerData(phoneNumber);

		assertNotNull(response);
		assertNull(response.getCustomer());
		assertTrue(response.getTransactions().isEmpty());
		assertTrue(response.getRewardPoints().isEmpty());
	}

	// Positive Scenario - to insert new customer data
	@Test
	public void testAddCustomer_Success() {

		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setPhoneNumber("1234567890");
		customerRequest.setName("Ashley Graham");
		customerRequest.setTransactions(List.of(new BigDecimal(150), new BigDecimal(120)));

		when(customerService.saveCustomer(any(CustomerRequest.class))).thenReturn(customer);
		ResponseEntity<Customer> response = rewardController.addCustomer(customerRequest);

		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		assertEquals("1234567890", response.getBody().getPhoneNumber());
	}

	// Negative Scenario - to insert new customer data, but customer alreayd present
	@Test
	public void testAddCustomer_Negative() {

		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setPhoneNumber("1234567890");
		customerRequest.setName("Ashley Graham");
		customerRequest.setTransactions(List.of(new BigDecimal(150), new BigDecimal(120)));

		when(customerService.saveCustomer(any(CustomerRequest.class)))
				.thenThrow(new RuntimeException("Customer already present"));

		Exception ex = assertThrows(RuntimeException.class, () -> {
			rewardController.addCustomer(customerRequest);
		});
		assertEquals("Customer already present", ex.getMessage());
	}
}
