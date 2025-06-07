package org.example.moneytransfer.service;

import org.example.moneytransfer.dto.ConfirmOperationRequest;
import org.example.moneytransfer.dto.TransferRequest;
import org.example.moneytransfer.dto.TransferResponse;
import org.example.moneytransfer.entity.Card;
import org.example.moneytransfer.entity.ConfirmOperation;
import org.example.moneytransfer.entity.Transfer;
import org.example.moneytransfer.exception.ConfirmationException;
import org.example.moneytransfer.exception.InputDataException;
import org.example.moneytransfer.exception.TransferException;
import org.example.moneytransfer.repository.CardRepository;
import org.example.moneytransfer.repository.ConfirmOperationRepository;
import org.example.moneytransfer.repository.TransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final ConfirmOperationRepository confirmOperationRepository;
    private final Logger logger = LoggerFactory.getLogger(TransferService.class);
    private final CardRepository cardRepository;
    private final Random random = new Random();

    public TransferService(TransferRepository transferRepository, CardRepository cardRepository, 
                           ConfirmOperationRepository confirmOperationRepository) {
        this.transferRepository = transferRepository;
        this.cardRepository = cardRepository;
        this.confirmOperationRepository = confirmOperationRepository;
    }

    @Transactional
    public TransferResponse transferMoney(TransferRequest request) {
        // Validate input data
        if (request.getCardFromNumber() == null || request.getCardFromValidTill() == null || 
            request.getCardFromCVV() == null || request.getCardToNumber() == null || 
            request.getAmount() == null || request.getAmount().getValue() == null || 
            request.getAmount().getCurrency() == null) {
            throw new InputDataException("Required fields are missing");
        }

        // Check if cards exist
        Optional<Card> cardFrom = cardRepository.findByNumber(request.getCardFromNumber());
        Optional<Card> cardTo = cardRepository.findByNumber(request.getCardToNumber());
        
        if (cardFrom.isEmpty()) {
            throw new InputDataException("Card from not found");
        }
        
        if (cardTo.isEmpty()) {
            throw new InputDataException("Card to not found");
        }
        
        // Validate card details
        Card fromCard = cardFrom.get();
        if (!fromCard.getValidTill().equals(request.getCardFromValidTill()) || 
            !fromCard.getCvv().equals(request.getCardFromCVV())) {
            throw new InputDataException("Invalid card details");
        }
        
        // Check amount
        if (request.getAmount().getValue() <= 0) {
            throw new InputDataException("Amount must be positive");
        }
        
        // Calculate commission (1%)
        double commission = request.getAmount().getValue() * 0.01;
        double totalAmount = request.getAmount().getValue() + commission;
        
        // Check balance
        if (fromCard.getBalance() < totalAmount) {
            throw new TransferException("Insufficient funds");
        }
        
        // Create transfer record
        Transfer transfer = new Transfer();
        transfer.setCardFromNumber(request.getCardFromNumber());
        transfer.setCardFromValidTill(request.getCardFromValidTill());
        transfer.setCardFromCVV(request.getCardFromCVV());
        transfer.setCardToNumber(request.getCardToNumber());
        transfer.setAmountValue(request.getAmount().getValue());
        transfer.setAmountCurrency(request.getAmount().getCurrency());
        transfer.setCommission(commission);
        transfer.setTransferDate(LocalDateTime.now());
        
        // Generate operation ID
        String operationId = UUID.randomUUID().toString();
        transfer.setOperationId(operationId);
        transfer.setStatus("PENDING");
        
        // Save the transfer
        transferRepository.save(transfer);
        
        // Log the transaction
        logTransaction(transfer, "PENDING");
        
        // Return operation ID
        return new TransferResponse(operationId);
    }

    @Transactional
    public TransferResponse confirmOperation(ConfirmOperationRequest request) {
        if (request.getOperationId() == null || request.getCode() == null) {
            throw new InputDataException("Operation ID and verification code are required");
        }
        
        // Find the transfer
        Optional<Transfer> transferOpt = transferRepository.findByOperationId(request.getOperationId());
        if (transferOpt.isEmpty()) {
            throw new InputDataException("Invalid operation ID");
        }
        
        Transfer transfer = transferOpt.get();
        
        // In a real system, we would validate the code against what was sent to the user
        // For this implementation, we'll just check if the code is "0000" (mock verification)
        if (!request.getCode().equals("0000")) {
            throw new ConfirmationException("Invalid verification code");
        }
        
        // Update card balances
        Card fromCard = cardRepository.findByNumber(transfer.getCardFromNumber())
                .orElseThrow(() -> new TransferException("Card from not found"));
        
        Card toCard = cardRepository.findByNumber(transfer.getCardToNumber())
                .orElseThrow(() -> new TransferException("Card to not found"));
        
        double totalAmount = transfer.getAmountValue() + transfer.getCommission();
        
        // Update balances
        fromCard.setBalance((int) (fromCard.getBalance() - totalAmount));
        toCard.setBalance(toCard.getBalance() + transfer.getAmountValue());
        
        // Save updated cards
        cardRepository.save(fromCard);
        cardRepository.save(toCard);
        
        // Update transfer status
        transfer.setStatus("COMPLETED");
        transferRepository.save(transfer);
        
        // Create confirmation record
        ConfirmOperation confirmation = new ConfirmOperation();
        confirmation.setOperationId(request.getOperationId());
        confirmation.setStatusOperation("COMPLETED");
        confirmOperationRepository.save(confirmation);
        
        // Log the completed transaction
        logTransaction(transfer, "COMPLETED");
        
        return new TransferResponse(request.getOperationId());
    }

    private void logTransaction(Transfer transfer, String result) {
        String logEntry = String.format("Date: %s, From Card: %s, To Card: %s, Amount: %d, Result: %s",
                LocalDateTime.now(), transfer.getCardFromNumber(), transfer.getCardToNumber(),
                transfer.getAmountValue(), result);

        // Log to file
        try (FileWriter writer = new FileWriter("operations.log", true)) {
            writer.write(logEntry + "\n");
        } catch (IOException e) {
            logger.error("Failed to write log", e);
        }
        
        // Log to console
        logger.info(logEntry);
    }
}
