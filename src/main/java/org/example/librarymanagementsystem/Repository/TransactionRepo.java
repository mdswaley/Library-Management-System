package org.example.librarymanagementsystem.Repository;

import org.example.librarymanagementsystem.Entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionEntity,Long> {

    @Query("SELECT t FROM TransactionEntity t WHERE t.book.id = :bookId AND t.user.id = :userId AND t.transactionType = 'ISSUE' ORDER BY t.timestamp DESC")
    List<TransactionEntity> findIssuedTransactions(@Param("bookId") Long bookId, @Param("userId") Long userId);

}
