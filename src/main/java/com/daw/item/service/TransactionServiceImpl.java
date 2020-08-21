package com.daw.item.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.daw.item.model.Item;
import com.daw.item.model.TransactionItemDetail;
import com.daw.item.model.TransactionItemHeader;
import com.daw.item.repository.ItemRepository;
import com.daw.item.repository.TransactionItemHeaderRepository;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionItemHeaderRepository transactionItemHeaderRepository;

	@Autowired
	private ItemRepository itemRepository;

	private final String TEMPLATE_NOT_FOUND = "Item not found for id = ";

	@Override
	public void addItem(TransactionItemHeader transactionHeader) {
		transactionItemHeaderRepository.save(transactionHeader);

		for (TransactionItemDetail transactionItemDetail : transactionHeader.getDetails()) {
			Item item = itemRepository.findById(transactionItemDetail.getItemId())
					.orElseThrow(() -> new RuntimeException(TEMPLATE_NOT_FOUND + transactionItemDetail.getItemId()));
			item.setQty(item.getQty() + transactionItemDetail.getQty());
			item.setUpdatedOn(new Date());
			item.setUpdatedBy(transactionHeader.getCreatedBy());
			itemRepository.save(item);
		}
	}

}