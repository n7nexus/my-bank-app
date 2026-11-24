package com.practicum.transfer.repository;

import com.practicum.transfer.model.TransferTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferTransactionRepository extends JpaRepository<TransferTransaction, Long> {
}
