package org.example.moneytransfer.repository;

import org.example.moneytransfer.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    Optional<Transfer> findById(Long id);
    Optional<Transfer> findByOperationId(String operationId);
}
