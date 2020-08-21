package com.daw.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daw.item.model.TransactionItemHeader;

@Repository
public interface TransactionItemHeaderRepository extends JpaRepository<TransactionItemHeader, Long> {
}
