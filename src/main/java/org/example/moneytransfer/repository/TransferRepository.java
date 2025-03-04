package org.example.moneytransfer.repository;

import org.example.moneytransfer.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    Optional<Transfer> findById(Long id);

    List<Transfer> findByStatus(String status);

    List<Transfer> findByCardFromNumber(String cardFromNumber);

    List<Transfer> findByCardToNumber(String cardToNumber);
}
