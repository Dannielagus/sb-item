package com.daw.item.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionItemHeaderDto {

	@NotNull(message = "Supplier Id cannot be null")
	private long supplierId;

	@NotNull(message = "Details cannot be null")
	private List<TransactionItemDetailDto> details;

}
