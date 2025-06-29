package com.github.timebetov.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {

    private Long id;
    private String accountNumber;
    private String email;
    private String ownerFirstName;
    private String ownerLastName;
    private Long balance;
}
