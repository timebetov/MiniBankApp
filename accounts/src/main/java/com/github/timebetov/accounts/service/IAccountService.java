package com.github.timebetov.accounts.service;

import com.github.timebetov.accounts.dto.AccountDTO;
import org.springframework.security.oauth2.jwt.Jwt;

public interface IAccountService {

    AccountDTO createAccountForUser(Jwt claims);
    AccountDTO getAccountForUserByEmail(String email);
    AccountDTO getAccountForUserById(String userId);
    Long depositForAccount(String accountNumber, Long amount);
    Long withdrawFromAccount(String accountNumber, Long amount);
}
