/**
 * 
 */
package com.retailer.rewardProgram.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */

@Data
@NoArgsConstructor
public class CustomerRequest {

	@Nonnull
	@NotBlank(message = "PhoneNumber cannot be blank")
	@Pattern(regexp = "\\d{10}", message = "Enter valid phoneNumber")
	private String phoneNumber;

	@NotBlank(message = "Name cannot be blank")
	private String name;

	private List<BigDecimal> transactions;
}
