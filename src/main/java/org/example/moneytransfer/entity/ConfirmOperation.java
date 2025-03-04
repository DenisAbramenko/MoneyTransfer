package org.example.moneytransfer.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "confirmation_operations")
public class ConfirmOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "operation_id", nullable = false)
    private String operationId;

    @Column(name = "status_operation", nullable = false)
    private String statusOperation;
}
