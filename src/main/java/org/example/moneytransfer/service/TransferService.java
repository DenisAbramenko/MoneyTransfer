package org.example.moneytransfer.service;

import org.example.moneytransfer.entity.ConfirmOperation;
import org.example.moneytransfer.entity.Transfer;
import org.example.moneytransfer.repository.TransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final Logger logger = LoggerFactory.getLogger(TransferService.class);

    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public String transferMoney(Transfer request) {
        String operationId = UUID.randomUUID().toString();
        request.setOperationId(operationId);
        request.setTransferDate(LocalDateTime.now());

        transferRepository.save(request);



        logTransaction(request, "SUCCESS");
        return request.getOperationId();
    }

    public String confirmOperation(ConfirmOperation request) {
        return request.getOperationId();
    }

    private void logTransaction(Transfer request, String result) {
        String logEntry = String.format("Date: %s, From Card: %s, To Card: %s, Amount: %d, Result: %s",
                LocalDateTime.now(), request.getCardFromNumber(), request.getCardToNumber(),
                request.getAmountValue(), result);

        // Логирование в файл
        try (FileWriter writer = new FileWriter("operations.log", true)) {
            writer.write(logEntry + "\n");
        } catch (IOException e) {
            logger.error("Failed to write log", e);
        }
    }
}
