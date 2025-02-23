package org.example.moneytransfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Amount {
    private int value;
    private String currency;
}
