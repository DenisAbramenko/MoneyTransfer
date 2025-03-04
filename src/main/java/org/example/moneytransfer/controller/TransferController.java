package org.example.moneytransfer.controller;

import org.example.moneytransfer.entity.ConfirmOperation;
import org.example.moneytransfer.entity.Transfer;
import org.example.moneytransfer.service.TransferService;
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
    public Object transferMoney(@RequestBody Transfer request) {
        return transferService.transferMoney(request);
    }

    @PostMapping("/confirmOperation")
    public Object confirmOperation(@RequestBody ConfirmOperation request) {
        return transferService.confirmOperation(request);
    }
}
