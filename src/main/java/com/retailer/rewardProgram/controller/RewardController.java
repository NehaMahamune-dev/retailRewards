/**
 * 
 */
package com.retailer.rewardProgram.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.rewardProgram.dto.CustomerRequest;
import com.retailer.rewardProgram.dto.CustomerRewardsResponse;
import com.retailer.rewardProgram.model.Customer;
import com.retailer.rewardProgram.model.Transactions;
import com.retailer.rewardProgram.service.CustomerService;
import com.retailer.rewardProgram.service.RewardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * rewards related URL mapping done in this class.
 * (/retail/rewards/{phoneNumber}) gives the reward points of that customer based on the phoneNumber 
 * provided.
 * 
 * (/retail/{phoneNumber}) gives all the data related to the customer
 * 
 * (/retail/add) enter new customer data
 */

@RestController
@RequestMapping("/retail")
@RequiredArgsConstructor
@Validated
public class RewardController {

	private final RewardService rewardService;
	private final CustomerService customerService;

	@GetMapping("/rewards/{phoneNumber}")
	public Map<String, Integer> getRewardsByCustomerId(@PathVariable String phoneNumber) {
		return rewardService.getRewards(phoneNumber);
	}
	
	@GetMapping("/{phoneNumber}")
	public CustomerRewardsResponse getCustomerData(@PathVariable String phoneNumber) {
		Customer customer = customerService.getCustomerData(phoneNumber);
		List<Transactions> transactions = customerService.getTransactionByPhone(phoneNumber);
		Map<String, Integer> rewards = rewardService.getRewards(phoneNumber);
		
		return new CustomerRewardsResponse(customer, transactions, rewards);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Customer> addCustomer(@Valid @RequestBody CustomerRequest request) {
		return ResponseEntity.ok(customerService.saveCustomer(request));
		
	}
}
