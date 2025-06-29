package com.github.timebetov.transactions.service;

import com.github.timebetov.dto.ResponseDTO;
import com.github.timebetov.transactions.dto.AccountDTO;
import com.github.timebetov.transactions.config.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "accounts", path = "/accounts", configuration = FeignClientConfiguration.class)
public interface AccountsFeignClient {

    @GetMapping("/{userId}")
    ResponseEntity<AccountDTO> getAccount(@PathVariable("userId") String userId);

    @PatchMapping("/{accountNumber}/withdraw")
    ResponseEntity<ResponseDTO> withdrawMoney(@PathVariable("accountNumber") String accountNumber,
                                                     @RequestParam(name = "amount") Long amount);

    @PatchMapping("/{accountNumber}/deposit")
    ResponseEntity<ResponseDTO> depositMoney(@PathVariable("accountNumber") String accountNumber,
                                                    @RequestParam(name = "amount") Long amount);
}
