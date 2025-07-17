package com.andre.picpaydesafio.repository;

import com.andre.picpaydesafio.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
