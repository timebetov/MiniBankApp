package com.github.timebetov.transactions.config;

import com.github.timebetov.transactions.dto.TransferDTO;
import com.github.timebetov.transactions.dto.TransferDetailsDTO;
import com.github.timebetov.transactions.models.Transfer;

public class TransferrMapper {

    private TransferrMapper() {}

    public static TransferDTO toDto(Transfer transfer) {

        return TransferDTO.builder()
                .transferId(transfer.getId())
                .from(transfer.getFromAccount())
                .to(transfer.getToAccount())
                .amount(transfer.getAmount())
                .message(transfer.getMessage())
                .transferredAt(transfer.getTransferredAt())
                .build();
    }

    public static Transfer fromDto(TransferDTO dto) {

        return Transfer.builder()
                .fromAccount(dto.getFrom())
                .toAccount(dto.getTo())
                .amount(dto.getAmount())
                .message(dto.getMessage())
                .build();
    }
}
