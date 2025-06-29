package com.github.timebetov.accounts.config;

import com.github.timebetov.accounts.dto.AccountDTO;
import com.github.timebetov.accounts.models.Account;

public class AccountMapper {

    private AccountMapper() {}

    public static AccountDTO toDTO(Account account) {

        return AccountDTO.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .email(account.getEmail())
                .ownerFirstName(account.getOwnerFirstName())
                .ownerLastName(account.getOwnerLastName())
                .balance(account.getBalance())
                .build();
    }
}
