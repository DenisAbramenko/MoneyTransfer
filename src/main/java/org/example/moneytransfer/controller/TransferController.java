package org.example.moneytransfer.controller;

import org.example.moneytransfer.dto.ConfirmOperationRequest;
import org.example.moneytransfer.dto.TransferRequest;
import org.example.moneytransfer.dto.TransferResponse;
import org.example.moneytransfer.service.TransferService;
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

    @PostMapping("/transfer")
    public ResponseEntity<?> transferMoney(@RequestBody TransferRequest request) {
        return ResponseEntity.ok(transferService.transferMoney(request));
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<?> confirmOperation(@RequestBody ConfirmOperationRequest request) {
        return ResponseEntity.ok(transferService.confirmOperation(request));
    }
}
