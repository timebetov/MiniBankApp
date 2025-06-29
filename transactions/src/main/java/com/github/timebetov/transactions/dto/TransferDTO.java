package com.github.timebetov.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferDTO {

    private Long transferId;
    private String from;
    private String to;
    private Long amount;
    private String message;
    private LocalDateTime transferredAt;
}
