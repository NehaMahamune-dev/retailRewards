/**
 * 
 */
package com.retailer.rewardProgram.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.retailer.rewardProgram.model.Customer;
import com.retailer.rewardProgram.model.Transactions;
import com.retailer.rewardProgram.repository.CustomerRepository;
import com.retailer.rewardProgram.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

/**
 * Customer and their transaction data to be loaded in this class'
 */

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

	private final TransactionRepository transactionRepo;
	private final CustomerRepository customerRepository;

	@Override
	public void run(String... args) throws Exception {
		loadSampleData();
	}

	private void loadSampleData() {
		if (customerRepository.count() == 0) {
			Customer c1 = new Customer("1234567890", "Ashley Graham", null);
			Customer c2 = new Customer("0987654321", "Barak Obama", null);

			List<Transactions> transactions1 = List.of(
					new Transactions(null, c1, new BigDecimal("120"), LocalDate.now().minusMonths(1)),
					new Transactions(null, c1, new BigDecimal("150"), LocalDate.now().minusMonths(2)),
					new Transactions(null, c1, new BigDecimal("110"), LocalDate.now().minusMonths(3))

			);

			List<Transactions> transactions2 = List.of(
					new Transactions(null, c2, new BigDecimal("75"), LocalDate.now().minusMonths(1)),
					new Transactions(null, c2, new BigDecimal("120"), LocalDate.now().minusMonths(2)),
					new Transactions(null, c2, new BigDecimal("145"), LocalDate.now().minusMonths(3))

			);
			
			transactionRepo.saveAll(transactions1);
			transactionRepo.saveAll(transactions2);
			
			c1.setTransactions(transactions1);
			c2.setTransactions(transactions2);
			
			customerRepository.saveAll(List.of(c1, c2));
		}

	}

}
