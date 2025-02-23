package org.example.librarymanagementsystem.Service;

import org.example.librarymanagementsystem.DTOs.TransactionDTO;
import org.example.librarymanagementsystem.Entity.BooksEntity;
import org.example.librarymanagementsystem.Entity.TransactionEntity;
import org.example.librarymanagementsystem.Entity.UserEntity;
import org.example.librarymanagementsystem.Entity.Enum.TransactionType;
import org.example.librarymanagementsystem.Exception.ResourceNotFoundException;
import org.example.librarymanagementsystem.Repository.BooksRepo;
import org.example.librarymanagementsystem.Repository.TransactionRepo;
import org.example.librarymanagementsystem.Repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final BooksRepo booksRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    public TransactionService(TransactionRepo transactionRepo, BooksRepo booksRepo, UserRepo userRepo, ModelMapper modelMapper) {
        this.transactionRepo = transactionRepo;
        this.booksRepo = booksRepo;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    public boolean isBookAvailable(Long id) {
        BooksEntity book = booksRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
        return book.getAvailableQuantity() > 0;
    }

    public TransactionDTO issueBook(Long bookId, Long userId) {
        BooksEntity book = booksRepo.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        if (!isBookAvailable(bookId)) {
            throw new ResourceNotFoundException("Book is not available for issue!");
        }

        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        book.setIssueQuantity(book.getIssueQuantity() + 1);
        booksRepo.save(book);

        TransactionEntity transaction = new TransactionEntity();
        transaction.setBook(book);
        transaction.setUser(user);
        transaction.setTransactionType(TransactionType.ISSUE);
        transaction.setIssueDate(LocalDateTime.now());
        transaction.setTimestamp(LocalDateTime.now());
        TransactionEntity savedTransaction = transactionRepo.save(transaction);

        return modelMapper.map(savedTransaction, TransactionDTO.class);
    }


    public TransactionDTO returnBook(Long bookId, Long userId) {
        BooksEntity book = booksRepo.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        List<TransactionEntity> issueTransactions = transactionRepo.findIssuedTransactions(bookId, userId);

        if (issueTransactions.isEmpty()) {
            throw new ResourceNotFoundException("This book was not issued to the user.");
        }

        if(book.getIssueQuantity() == 0){
            throw new ResourceNotFoundException("Already Return Book.");
        }

        TransactionEntity issueTransaction = issueTransactions.get(0);

        LocalDateTime returnDate = LocalDateTime.now();
        long daysLate = ChronoUnit.DAYS.between(issueTransaction.getIssueDate(), returnDate) - 14;
        double fine = daysLate > 0 ? daysLate * 10.0 : 0.0;

        book.setIssueQuantity(book.getIssueQuantity() - 1);
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        booksRepo.save(book);

        TransactionEntity returnTransaction = new TransactionEntity();
        returnTransaction.setBook(book);
        returnTransaction.setUser(user);
        returnTransaction.setTransactionType(TransactionType.RETURN);
        returnTransaction.setIssueDate(issueTransaction.getIssueDate());
        returnTransaction.setReturnDate(returnDate);
        returnTransaction.setFine(fine);
        returnTransaction.setTimestamp(returnDate);

        TransactionEntity savedTransaction = transactionRepo.save(returnTransaction);

        return modelMapper.map(savedTransaction, TransactionDTO.class);
    }



}
