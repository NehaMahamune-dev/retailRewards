/**
 * 
 */
package com.retailer.rewardProgram.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transaction class takes the customer data and the transactions done by
 * particular customer. It has ID as a primary key which is auto-generated,
 * customer data, amount spend by customer and the date of the transaction.
 * 
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_phoneNumber", referencedColumnName = "phoneNumber")
	private Customer customer;
	private BigDecimal amount;
	private LocalDate date;

}
