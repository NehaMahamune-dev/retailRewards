/**
 * 
 */
package com.retailer.rewardProgram.dto;

import java.util.List;
import java.util.Map;

import com.retailer.rewardProgram.model.Customer;
import com.retailer.rewardProgram.model.Transactions;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 */

@Data
@AllArgsConstructor
public class CustomerRewardsResponse {

	private Customer customer;
	private List<Transactions> transactions;
	Map<String, Integer> rewardPoints;
}
