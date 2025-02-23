package org.example.moneytransfer.controller;

import org.example.moneytransfer.model.ConfirmOperationRequest;
import org.example.moneytransfer.model.TransferRequest;
import org.example.moneytransfer.model.TransferResponse;
import org.example.moneytransfer.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<TransferResponse> transferMoney(@RequestBody TransferRequest request) {
        TransferResponse response = transferService.transferMoney(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<TransferResponse> confirmOperation(@RequestBody ConfirmOperationRequest request) {
        TransferResponse response = transferService.confirmOperation(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
