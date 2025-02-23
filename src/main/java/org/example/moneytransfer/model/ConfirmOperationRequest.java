package org.example.moneytransfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfirmOperationRequest {
    private String operationId;
    private String code;
}
