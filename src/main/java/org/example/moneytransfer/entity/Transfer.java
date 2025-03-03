package org.example.moneytransfer.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "status", nullable = false)
    private String status; // Status: SUCCESS, PENDING, FAILED
}
