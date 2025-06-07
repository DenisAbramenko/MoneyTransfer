package org.example.moneytransfer.dto;

import lombok.Data;
import org.example.moneytransfer.entity.Amount;

@Data
public class TransferRequest {
    private String cardFromNumber;
    private String cardFromValidTill;
    private String cardFromCVV;
    private String cardToNumber;
    private Amount amount;
} 