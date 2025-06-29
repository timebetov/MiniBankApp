package com.github.timebetov.accounts.repository;

import com.github.timebetov.accounts.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);
    Optional<Account> findByKeycloakUserId(String userId);
    Optional<Account> findByAccountNumber(String accountNumber);
}
