package com.github.timebetov.accounts.controller;

import com.github.timebetov.accounts.dto.AccountDTO;
import com.github.timebetov.accounts.service.IAccountService;
import com.github.timebetov.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    private final IAccountService service;

    @PostMapping
    public ResponseEntity<AccountDTO> createNewAccount(@AuthenticationPrincipal Jwt details) {

        AccountDTO accountDetails = service.createAccountForUser(details);
        return ResponseEntity.ok(accountDetails);
    }

    @PatchMapping("/{accountNumber}/withdraw")
    public ResponseEntity<ResponseDTO> withdrawMoney(@PathVariable("accountNumber") String accountNumber,
                                                     @RequestParam(name = "amount") Long amount) {

        Long newBalance = service.withdrawFromAccount(accountNumber, amount);
        ResponseDTO response = ResponseDTO.builder()
                .statusCode(HttpStatus.OK)
                .message("You have successfully withdrew " + amount + " funds. Balance: " + newBalance)
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{accountNumber}/deposit")
    public ResponseEntity<ResponseDTO> depositMoney(@PathVariable("accountNumber") String accountNumber,
                                                    @RequestParam(name = "amount") Long amount) {

        Long newBalance = service.depositForAccount(accountNumber, amount);
        ResponseDTO response = ResponseDTO.builder()
                .statusCode(HttpStatus.OK)
                .message("You have successfully deposit " + amount + " funds. Balance: " + newBalance)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<AccountDTO> getMyAccount(@AuthenticationPrincipal Jwt details) {

        AccountDTO accountDetails = service.getAccountForUserByEmail(details.getClaimAsString("email"));
        return ResponseEntity.ok(accountDetails);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable("userId") String userId) {

        AccountDTO accountDetails = service.getAccountForUserById(userId);
        return ResponseEntity.ok(accountDetails);
    }
}
