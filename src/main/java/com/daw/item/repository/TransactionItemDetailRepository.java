package com.daw.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daw.item.model.TransactionItemDetail;

@Repository
public interface TransactionItemDetailRepository extends JpaRepository<TransactionItemDetail, Long> {
}
