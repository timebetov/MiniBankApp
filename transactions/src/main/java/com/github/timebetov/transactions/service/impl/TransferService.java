package com.github.timebetov.transactions.service.impl;

import com.github.timebetov.exceptions.ResourceNotFoundException;
import com.github.timebetov.transactions.config.TransferrMapper;
import com.github.timebetov.transactions.dto.AccountDTO;
import com.github.timebetov.transactions.dto.TransferDTO;
import com.github.timebetov.transactions.dto.TransferDetailsDTO;
import com.github.timebetov.transactions.models.Transfer;
import com.github.timebetov.transactions.repository.TransferRepository;
import com.github.timebetov.transactions.service.AccountsFeignClient;
import com.github.timebetov.transactions.service.ITransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferService implements ITransferService {

    private final TransferRepository repository;
    private final AccountsFeignClient client;

    @Override
    public List<TransferDTO> getTransfers() {

        return repository.findAll().stream().map(TransferrMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public TransferDTO getTransferWithId(Long id) {

        return TransferrMapper.toDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transfer",
                id.toString())));
    }

    @Override
    public TransferDTO executeTransfer(String userId, TransferDetailsDTO transferDetails) {

        // Minimum amount to transfer is 100
        if (transferDetails.getAmount() < 0) {
            throw new IllegalArgumentException("Minimum funds to transfer is 100");
        }

        // Getting AccountDetails from Accounts service
        ResponseEntity<AccountDTO> resultOfAccount = client.getAccount(userId);
        AccountDTO accountDetails = resultOfAccount.getBody();
        if (accountDetails == null) {
            throw new ResourceNotFoundException("Account", userId);
        }

        // If user try to transfer to himself
        if (accountDetails.getAccountNumber().equals(transferDetails.getAccountNumberToTransfer())) {
            throw new IllegalArgumentException("Customer can't transfer money to himself");
        }

        // REST Call to Withdraw funds
        client.withdrawMoney(accountDetails.getAccountNumber(), transferDetails.getAmount());
        client.depositMoney(transferDetails.getAccountNumberToTransfer(), transferDetails.getAmount());

        Transfer newTransfer = Transfer.builder()
                .fromAccount(accountDetails.getAccountNumber())
                .toAccount(transferDetails.getAccountNumberToTransfer())
                .amount(transferDetails.getAmount())
                .message(transferDetails.getMessage())
                .transferredAt(LocalDateTime.now())
                .build();
        newTransfer = repository.save(newTransfer);

        return TransferrMapper.toDto(newTransfer);
    }
}
