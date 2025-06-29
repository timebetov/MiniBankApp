package com.github.timebetov.transactions.controller;

import com.github.timebetov.transactions.dto.TransferDTO;
import com.github.timebetov.transactions.dto.TransferDetailsDTO;
import com.github.timebetov.transactions.service.ITransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

    private final ITransferService service;

    @GetMapping
    public ResponseEntity<List<TransferDTO>> getTransfers() {

        return ResponseEntity.ok(service.getTransfers());
    }

    @GetMapping("/{transferId}")
    public ResponseEntity<TransferDTO> getTransfer(@PathVariable("transferId") Long transferId) {

        return ResponseEntity.ok(service.getTransferWithId(transferId));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferDTO> transferMoney(@AuthenticationPrincipal Jwt authenticatedUserDetails,
                                                     @RequestBody @Valid TransferDetailsDTO transferDetails) {

        String userId = authenticatedUserDetails.getClaimAsString("sub");
        TransferDTO transferDetailsDto = service.executeTransfer(userId, transferDetails);
        return ResponseEntity.ok(transferDetailsDto);
    }
}
