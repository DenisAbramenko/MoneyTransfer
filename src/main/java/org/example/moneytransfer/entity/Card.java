package org.example.moneytransfer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Card {
    @Id
    @Column(name = "number")
    private String number;

    @Column(name = "valid_till")
    private String validTill;

    @Column(name = "cvv")
    private String cvv;

    @Column(name = "balance")
    private int balance;
}
