package org.example.moneytransfer.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "card_from_number", nullable = false)
    private String cardFromNumber;

    @Column(name = "card_to_number", nullable = false)
    private String cardToNumber;

    @Column(name = "amount_value", nullable = false)
    private int amountValue;

    @Column(name = "amount_currency", nullable = false)
    private String amountCurrency;

    @Column(name = "commission")
    private double commission;

    @Column(name = "transfer_date", nullable = false)
    private LocalDateTime transferDate;

    @Column(name = "operation_id", nullable = false)
    private String operationId;
}
