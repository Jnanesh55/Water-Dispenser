package com.example.WaterDispenser.Repository;

import com.example.WaterDispenser.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository <Transaction,Long> {
  Transaction findByPaymentId(String paymentId);
}
