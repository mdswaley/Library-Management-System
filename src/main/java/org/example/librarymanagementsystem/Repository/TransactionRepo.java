package org.example.librarymanagementsystem.Repository;

import org.example.librarymanagementsystem.Entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionEntity,Long> {
}
