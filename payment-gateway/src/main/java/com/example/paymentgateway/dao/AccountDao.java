package com.example.paymentgateway.dao;

import com.example.paymentgateway.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountDao extends JpaRepository<Account, Integer> {

    Optional<Account> findByEmail(String string);
}
