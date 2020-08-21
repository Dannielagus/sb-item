package com.daw.item.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "transaction_item_header")
public class TransactionItemHeader {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "supplier_id")
	private long supplierId;

	@OneToMany(targetEntity = TransactionItemDetail.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "transaction_item_header_id", referencedColumnName = "id")
	private List<TransactionItemDetail> details;

	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Column(name = "created_by")
	private Long createdBy;

}
