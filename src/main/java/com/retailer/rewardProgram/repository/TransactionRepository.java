/**
 * 
 */
package com.retailer.rewardProgram.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retailer.rewardProgram.model.Transactions;

/**
 * 
 */

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Long> {

	List<Transactions> findByCustomer_phoneNumberAndDateBetween(String phoneNumber, LocalDate startDate,
			LocalDate endDate);

	List<Transactions> findByCustomer_phoneNumber(String phoneNumber);
}
