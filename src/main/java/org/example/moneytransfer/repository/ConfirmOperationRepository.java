package org.example.moneytransfer.repository;

import org.example.moneytransfer.entity.ConfirmOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmOperationRepository extends JpaRepository<ConfirmOperation, Long> {
}
