package com.daw.item.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemDto {

	@NotNull(message = "Item Name cannot be null")
	@Size(min = 1, max = 100, message = "Minimum 1 characters")
	private String itemName;

	@NotNull(message = "Barcode cannot be null")
	@Size(min = 1, max = 50, message = "Minimum 1 characters")
	private String barcode;

}
