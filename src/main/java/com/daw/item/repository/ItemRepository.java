package com.daw.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daw.item.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
