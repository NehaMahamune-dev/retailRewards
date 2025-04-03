/**
 * 
 */
package com.retailer.rewardProgram.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Customer class to represent the customer info and their corresponding
 * transactions. Customer has phone number and name. Here, Phone number is the
 * id that is manually added PhoneNumber is different for each customer. Each
 * customer can have multiple transactions.
 * 
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	@Id
	private String phoneNumber;
	private String name;
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Transactions> transactions;

}
