package com.github.timebetov.transactions.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferDetailsDTO {

    @NotBlank(message = "Account Number To Transfer is required")
    private String accountNumberToTransfer;

    @NotNull(message = "Amount is required")
    private Long amount;

    @Size(max = 50, message = "Maximum length of message is 50 characters")
    private String message;
}
