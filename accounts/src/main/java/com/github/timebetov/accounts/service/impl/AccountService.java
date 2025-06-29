package com.github.timebetov.accounts.service.impl;

import com.github.timebetov.accounts.config.AccountMapper;
import com.github.timebetov.accounts.dto.AccountDTO;
import com.github.timebetov.accounts.models.Account;
import com.github.timebetov.accounts.repository.AccountRepository;
import com.github.timebetov.accounts.service.IAccountService;
import com.github.timebetov.exceptions.AlreadyExistsException;
import com.github.timebetov.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final AccountRepository repository;

    @Override
    public AccountDTO createAccountForUser(Jwt claims) {

        String keyCloakUserId = claims.getClaimAsString("sub");
        if (repository.findByKeycloakUserId(keyCloakUserId).isPresent()) {
            throw new AlreadyExistsException("Account", keyCloakUserId);
        }

        Account newAccount = new Account();
        newAccount.setAccountNumber(UUID.randomUUID().toString());
        newAccount.setKeycloakUserId(keyCloakUserId);
        newAccount.setEmail(claims.getClaimAsString("email"));
        newAccount.setOwnerFirstName(claims.getClaimAsString("given_name"));
        newAccount.setOwnerLastName(claims.getClaimAsString("family_name"));
        newAccount.setBalance(0L);

        Account savedAccount = repository.save(newAccount);
        return AccountMapper.toDTO(savedAccount);
    }

    @Override
    public AccountDTO getAccountForUserByEmail(String email) {

        Account foundAccount = repository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Not Found with given email: " + email));
        return AccountMapper.toDTO(foundAccount);
    }

    @Override
    public AccountDTO getAccountForUserById(String userId) {

        Account foundAccount = repository.findByKeycloakUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("Account", userId));
        return AccountMapper.toDTO(foundAccount);
    }

    @Override
    public Long depositForAccount(String accountNumber, Long amount) {

        if (amount < 100) {
            throw new IllegalArgumentException("Minimum amount for deposit is 100.");
        }
        Account foundAccount = repository.findByAccountNumber(accountNumber).orElseThrow(
                () -> new ResourceNotFoundException("Account", accountNumber));
        foundAccount.setBalance(foundAccount.getBalance() + amount);
        repository.save(foundAccount);
        return foundAccount.getBalance();
    }

    @Override
    public Long withdrawFromAccount(String accountNumber, Long amount) {

        if (amount < 100) {
            throw new IllegalArgumentException("Minimum amount for withdraw is 100");
        }
        Account foundAccount = repository.findByAccountNumber(accountNumber).orElseThrow(
                () -> new ResourceNotFoundException("Account", accountNumber));
        if (foundAccount.getBalance() < amount) {
            throw new IllegalArgumentException("You don't have enough funds to withdraw");
        }
        foundAccount.setBalance(foundAccount.getBalance() - amount);
        repository.save(foundAccount);
        return foundAccount.getBalance();
    }
}
