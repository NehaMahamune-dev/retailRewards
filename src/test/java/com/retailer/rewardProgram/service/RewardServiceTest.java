/**
 * 
 */
package com.retailer.rewardProgram.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.retailer.rewardProgram.model.Customer;
import com.retailer.rewardProgram.model.Transactions;
import com.retailer.rewardProgram.repository.TransactionRepository;

/**
 * TEst class for RewardService
 * 
 */
public class RewardServiceTest {

	@Mock
	private TransactionRepository transactionRepo;

	@InjectMocks
	private RewardService rewardService;

	private Customer customer;
	private List<Transactions> transactions;
	Map<String, Integer> rewards;
	String phoneNumber = "1234567890";

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

	// positive sceanrio to fetch the rewards
	@Test
	public void getRewardsTest_Success() {
		LocalDate date = LocalDate.now();
		when(transactionRepo.findByCustomer_phoneNumberAndDateBetween(eq(phoneNumber), any(LocalDate.class),
				any(LocalDate.class))).thenReturn(transactions);
		Map<String, Integer> rewards = rewardService.getRewards(phoneNumber);
		assertNotNull(rewards);
		assertEquals(3, rewards.size());
		assertTrue(rewards.containsKey(YearMonth.from(date.minusMonths(1)).toString()));
		assertTrue(rewards.containsKey(YearMonth.from(date.minusMonths(2)).toString()));
	}

	// Negative sceanrio to fetch the rewards
	// Mocks the repository to return an empty list for a non-existent phone number
	// or no transactions and asserts that the returned rewards map contains zero
	// points for the last 3 months.

	@Test
	public void getRewardsTest_Fail() {
		when(transactionRepo.findByCustomer_phoneNumberAndDateBetween(eq(phoneNumber), any(LocalDate.class),
				any(LocalDate.class))).thenReturn(Collections.emptyList());
		Map<String, Integer> rewards = rewardService.getRewards(phoneNumber);
		assertNotNull(rewards);
		assertEquals(3, rewards.size());
		assertTrue(rewards.values().stream().allMatch(points -> points == 0));
	}
}
