package com.daw.item.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Supplier {

	private long id;

	private String supplierName;

	private String phoneNo;

	private String email;

	private Date createdOn;

	private Long createdBy;

	private Date updatedOn;

	private Long updatedBy;
}
