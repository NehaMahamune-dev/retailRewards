package com.retailer.rewardProgram.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.retailer.rewardProgram.model.Customer;
import com.retailer.rewardProgram.model.Transactions;
import com.retailer.rewardProgram.repository.CustomerRepository;
import com.retailer.rewardProgram.repository.TransactionRepository;

/**
 * TEst class for CustomerService
 * 
 */
public class CustomerServiceTest {

	@Mock
	private TransactionRepository transactionRepo;

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerService customerService;

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

	// Positive scenario to get Customer data
	// Mocks the repository to return a valid `Customer` for a given phone number
	// and asserts that the returned customer matches the expected data.
	@Test
	public void getCustomerDataTest_Success() {
		String phoneNumber = "1234567890";
		Customer mockCustomer = new Customer("Ashley Graham", phoneNumber, null);
		when(customerRepository.findById(phoneNumber)).thenReturn(Optional.of(mockCustomer));
		Customer customer = customerService.getCustomerData(phoneNumber);
		assertNotNull(customer);
		assertEquals(mockCustomer, customer);
	}

	// Negative scenario to get Customer data, but no customer found
	@Test
	public void getCustomerDataTest_Fail() {
		String phoneNumber = "0987654321";
		when(customerRepository.findById(phoneNumber)).thenReturn(Optional.empty());
		Exception exception = assertThrows(RuntimeException.class, () -> {
			customerService.getCustomerData(phoneNumber);
		});
		String expectedMessage = "Customer Not Found";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	// Positive scenario to get Transaction data by phoneNumber
	@Test
	public void getTransactionByPhone_Success() {
		String phoneNumber = "1234567890";
		when(transactionRepo.findByCustomer_phoneNumber(phoneNumber)).thenReturn(transactions);
		List<Transactions> transactions1 = customerService.getTransactionByPhone(phoneNumber);
		assertNotNull(transactions);
		assertEquals(transactions, transactions1);
	}

	// Negative scenario to get Transaction data by phoneNumber
	@Test
	public void getTransactionByPhone_Fail() {
		String phoneNumber = "0987654321";
		when(transactionRepo.findByCustomer_phoneNumber(phoneNumber)).thenReturn(Collections.emptyList());
		List<Transactions> transactions = customerService.getTransactionByPhone(phoneNumber);
		assertNotNull(transactions);
		assertTrue(transactions.isEmpty());
	}

}
