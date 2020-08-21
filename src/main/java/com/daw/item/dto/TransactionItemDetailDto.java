package com.daw.item.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionItemDetailDto {

	@NotNull(message = "Qty cannot be null")
	private long qty;

	@NotNull(message = "Item Id cannot be null")
	private long itemId;

}
