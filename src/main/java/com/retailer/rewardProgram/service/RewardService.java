/**
 * 
 */
package com.retailer.rewardProgram.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
		List<Transactions> transactions = transactionRepo.findByCustomer_phoneNumber(phoneNumber);
		rewards = transactions.stream().collect(Collectors.groupingBy(t -> t.getDate().getMonth().toString(),
				Collectors.summingInt(t -> calculatePoints(t.getAmount()))));
		int totalRewards = rewards.values().stream().mapToInt(Integer::intValue).sum();
		Map<String, Integer> finalRewards = new LinkedHashMap<>(rewards);
		finalRewards.put("Total", totalRewards);
		return finalRewards;
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
