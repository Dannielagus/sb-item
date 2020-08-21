package com.daw.item.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daw.item.dto.ItemDto;
import com.daw.item.exception.ResourceNotFoundException;
import com.daw.item.model.DataToken;
import com.daw.item.model.Item;
import com.daw.item.repository.ItemRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ItemController {

	@Autowired
	private ItemRepository itemRepository;

	private final String TEMPLATE_NOT_FOUND = "Item not found for id = ";
	private final String PATH = "/item";

	@GetMapping(PATH)
	public Page<Item> getAllItem(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Page<Item> findAll = itemRepository.findAll(PageRequest.of(page, size));
		return (Page<Item>) findAll;
	}

	@GetMapping(PATH + "/{id}")
	public ResponseEntity<Item> get(@PathVariable Long id) throws ResourceNotFoundException {
		Item item = itemRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(TEMPLATE_NOT_FOUND + id));
		return ResponseEntity.ok().body(item);
	}

	@PostMapping(PATH)
	public ResponseEntity<Item> createItem(@Valid @RequestBody ItemDto itemDto) {
		Item item = new Item();
		item.setItemName(itemDto.getItemName());
		item.setBarcode(itemDto.getBarcode());

		item.setCreatedOn(new Date());
		item.setCreatedBy(((DataToken) SecurityContextHolder.getContext().getAuthentication().getDetails()).getId());
		return ResponseEntity.ok(itemRepository.save(item));
	}

	@PutMapping(PATH + "/{id}")
	public ResponseEntity<Item> updateItem(@PathVariable Long id, @Valid @RequestBody ItemDto itemDto)
			throws ResourceNotFoundException {
		Item item = itemRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(TEMPLATE_NOT_FOUND + id));
		item.setItemName(itemDto.getItemName());
		item.setBarcode(itemDto.getBarcode());

		item.setUpdatedOn(new Date());
		item.setUpdatedBy(((DataToken) SecurityContextHolder.getContext().getAuthentication().getDetails()).getId());
		return ResponseEntity.ok(itemRepository.save(item));
	}

	@DeleteMapping(PATH + "/{id}")
	public ResponseEntity<?> deleteItem(@PathVariable Long id) throws ResourceNotFoundException {
		Item item = itemRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(TEMPLATE_NOT_FOUND + id));
		itemRepository.delete(item);
		return ResponseEntity.noContent().build();
	}

}
