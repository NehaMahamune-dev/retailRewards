/**
 * 
 */
package com.retailer.rewardProgram.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.retailer.rewardProgram.dto.CustomerRequest;
import com.retailer.rewardProgram.model.Customer;
import com.retailer.rewardProgram.model.Transactions;
import com.retailer.rewardProgram.repository.CustomerRepository;
import com.retailer.rewardProgram.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

/**
 * 
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

	private final TransactionRepository transactionRepo;
	private final CustomerRepository customerRepository;

	public Customer getCustomerData(String phoneNumber) {
		return customerRepository.findById(phoneNumber).orElseThrow(() -> new RuntimeException("Customer Not Found"));
	}

	public List<Transactions> getTransactionByPhone(String phoneNumber) {
		return transactionRepo.findByCustomer_phoneNumber(phoneNumber);
	}

	public Customer saveCustomer(CustomerRequest request) {
		Customer customer = new Customer();
		customer.setPhoneNumber(request.getPhoneNumber());
		customer.setName(request.getName());
		customer = customerRepository.save(customer);
		for(BigDecimal amount : request.getTransactions()) {
			Transactions transactions = new Transactions(null, customer, amount, LocalDate.now());
			transactionRepo.save(transactions);
		}
		return customer;
	}

}
