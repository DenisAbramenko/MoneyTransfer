package org.example.moneytransfer.service;

import org.example.moneytransfer.entity.Card;
import org.example.moneytransfer.entity.ConfirmOperation;
import org.example.moneytransfer.entity.Transfer;
import org.example.moneytransfer.exception.InputDataException;
import org.example.moneytransfer.exception.TransferException;
import org.example.moneytransfer.repository.CardRepository;
import org.example.moneytransfer.repository.TransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final Logger logger = LoggerFactory.getLogger(TransferService.class);
    private final CardRepository cardRepository;

    public TransferService(TransferRepository transferRepository, CardRepository cardRepository) {
        this.transferRepository = transferRepository;
        this.cardRepository = cardRepository;
    }

    public String transferMoney(Transfer request) {
        //Генерация данных от ID
        String operationId = UUID.randomUUID().toString();
        request.setOperationId(operationId);
        request.setTransferDate(LocalDateTime.now());

        transferRepository.save(request);

        // Проверка карт
        Optional<Card> cardFrom = cardRepository.findByCardNumber(request.getCardFromNumber());
        Optional<Card> cardTo = cardRepository.findByCardNumber(request.getCardToNumber());
        if (cardFrom.isEmpty() || cardTo.isEmpty()) {
            throw new InputDataException("Invalid card number");
        }

        int cardFromBalance = cardFrom.get().getBalance();
        int cardToBalance = cardTo.get().getBalance();
        double amountTransfer = request.getAmountValue() + request.getCommission();

        // Проверка баланса отправителя
        if (cardFromBalance < amountTransfer){
            throw new TransferException("There is not enough balance for the transfer");
        }

        //todo Выполнение перевода денежных средств

        //todo Логирование операции



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
