/**
 * 
 */
package com.retailer.rewardProgram.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.retailer.rewardProgram.model.Transactions;
import com.retailer.rewardProgram.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

/**
 * 
 */

@Service
@RequiredArgsConstructor
public class RewardService {
	private final TransactionRepository transactionRepo;

	public Map<String, Integer> getRewards(String phoneNumber) {
		Map<String, Integer> rewards = new HashMap<>();
		LocalDate date = LocalDate.now();

		for (int i = 0; i < 3; i++) {
			YearMonth yearMonth = YearMonth.from(date.minusMonths(i));
			LocalDate start = yearMonth.atDay(1);
			LocalDate end = yearMonth.atEndOfMonth();

			List<Transactions> transactions = transactionRepo.findByCustomer_phoneNumberAndDateBetween(phoneNumber,
					start, end);

			int points = transactions.stream().mapToInt(t -> calculatePoints(t.getAmount())).sum();
			rewards.put(yearMonth.toString(), points);
		}
		return null;
	}

	private int calculatePoints(BigDecimal amount) {
		int points = 0;
		int money = amount.intValue();
		if (money > 100) {
			points += (money - 100) * 2;
			money = 100;
		}
		if (money > 50) {
			points += (money - 50);
		}
		return points;
	}

}
