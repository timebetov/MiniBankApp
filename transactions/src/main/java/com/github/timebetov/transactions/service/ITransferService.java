package com.github.timebetov.transactions.service;

import com.github.timebetov.transactions.dto.TransferDTO;
import com.github.timebetov.transactions.dto.TransferDetailsDTO;

import java.util.List;

public interface ITransferService {

    List<TransferDTO> getTransfers();
    TransferDTO getTransferWithId(Long id);
    TransferDTO executeTransfer(String userId, TransferDetailsDTO transferDetails);
}
