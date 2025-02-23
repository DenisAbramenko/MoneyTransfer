package org.example.moneytransfer.service;

import org.example.moneytransfer.dto.Transfer;
import org.example.moneytransfer.model.ConfirmOperationRequest;
import org.example.moneytransfer.model.TransferRequest;
import org.example.moneytransfer.model.TransferResponse;
import org.example.moneytransfer.repository.TransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final Logger logger = LoggerFactory.getLogger(TransferService.class);

    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public TransferResponse transferMoney(TransferRequest request) {
        logTransaction(request, "SUCCESS");
        return new TransferResponse("operation-id-123");
    }

    public TransferResponse confirmOperation(ConfirmOperationRequest request) {
        return new TransferResponse("operation-id-123");
    }

    private void logTransaction(TransferRequest request, String result) {
        String logEntry = String.format("Date: %s, From Card: %s, To Card: %s, Amount: %d, Result: %s",
                LocalDateTime.now(), request.getCardFromNumber(), request.getCardToNumber(),
                request.getAmount().getValue(), result);

        // Логирование в файл
        try (FileWriter writer = new FileWriter("operations.log", true)) {
            writer.write(logEntry + "\n");
        } catch (IOException e) {
            logger.error("Failed to write log", e);
        }
    }

    // Метод для сохранения перевода
    public Transfer saveTransfer(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    // Метод для поиска перевода по ID
    public Optional<Transfer> getTransferById(Long id) {
        return transferRepository.findById(id);
    }

    // Метод для поиска переводов по статусу
    public List<Transfer> getTransfersByStatus(String status) {
        return transferRepository.findByStatus(status);
    }
}
