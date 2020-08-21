package com.daw.item.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.daw.item.dto.TransactionItemDetailDto;
import com.daw.item.dto.TransactionItemHeaderDto;
import com.daw.item.exception.ResourceNotFoundException;
import com.daw.item.model.DataToken;
import com.daw.item.model.Supplier;
import com.daw.item.model.TransactionItemDetail;
import com.daw.item.model.TransactionItemHeader;
import com.daw.item.repository.TransactionItemHeaderRepository;
import com.daw.item.service.TransactionService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class TransactionItemController {

	@Autowired
	private TransactionItemHeaderRepository transactionItemHeaderRepository;

	@Autowired
	private TransactionService transactionService;

	@Value("${supplier.service.url}")
	private String supplierServiceUrl;

	@Autowired
	private RestTemplate template;

	@GetMapping("/transaction")
	public Page<TransactionItemHeader> getAllTransaction(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Page<TransactionItemHeader> findAll = transactionItemHeaderRepository.findAll(PageRequest.of(page, size));
		return (Page<TransactionItemHeader>) findAll;
	}

	@PostMapping("/transaction-item-add")
	public ResponseEntity<?> addTransactionItem(@Valid @RequestBody TransactionItemHeaderDto transactionHeaderDto)
			throws URISyntaxException, ResourceNotFoundException {

		String url = supplierServiceUrl + "/supplier/" + transactionHeaderDto.getSupplierId();

		HttpEntity<?> request = new HttpEntity(getHeaders());
		template.exchange(url, HttpMethod.GET, request, Supplier.class, 1);

		transactionService.addItem(getTransactionItemHeader(transactionHeaderDto));
		return ResponseEntity.created(new URI("")).build();
	}

	private TransactionItemHeader getTransactionItemHeader(TransactionItemHeaderDto transactionHeaderDto) {
		TransactionItemHeader transactionHeader = new TransactionItemHeader();
		transactionHeader.setSupplierId(transactionHeaderDto.getSupplierId());
		transactionHeader.setCreatedOn(new Date());
		transactionHeader.setCreatedBy(
				((DataToken) SecurityContextHolder.getContext().getAuthentication().getDetails()).getId());

		List<TransactionItemDetail> details = new ArrayList<>();
		for (TransactionItemDetailDto detailDto : transactionHeaderDto.getDetails()) {
			TransactionItemDetail dtl = new TransactionItemDetail();
			dtl.setItemId(detailDto.getItemId());
			dtl.setQty(detailDto.getQty());
			details.add(dtl);
		}
		transactionHeader.setDetails(details);
		return transactionHeader;
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(
				((DataToken) SecurityContextHolder.getContext().getAuthentication().getDetails()).getToken());
		return headers;
	}
}
