package org.example.librarymanagementsystem.Service;

import org.example.librarymanagementsystem.DTOs.BooksDTO;
import org.example.librarymanagementsystem.Entity.BooksEntity;
import org.example.librarymanagementsystem.Entity.TransactionEntity;
import org.example.librarymanagementsystem.Entity.Enum.TransactionType;
import org.example.librarymanagementsystem.Exception.ResourceNotFoundException;
import org.example.librarymanagementsystem.Repository.BooksRepo;
import org.example.librarymanagementsystem.Repository.TransactionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final BooksRepo booksRepo;
    private final ModelMapper modelMapper;

    public TransactionService(TransactionRepo transactionRepo, BooksRepo booksRepo, ModelMapper modelMapper) {
        this.transactionRepo = transactionRepo;
        this.booksRepo = booksRepo;
        this.modelMapper = modelMapper;
    }

    public boolean isBookAvailable(Long id) {
        BooksEntity book = booksRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
        return book.getQuantity() > 0;
    }

    public BooksDTO issueBook(Long bookId, Long userId) {
        BooksEntity book = booksRepo.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

        if (!isBookAvailable(bookId)) {
            throw new IllegalArgumentException("Book is not available for issue!");
        }

        if(book.getQuantity() > 1){
            book.setQuantity(book.getQuantity() - 1);
        }else{
            throw new ResourceNotFoundException("Book is unavailable.");
        }

        BooksEntity updatedBook = booksRepo.save(book);

        TransactionEntity transaction = new TransactionEntity();
        transaction.setBook(book);
        transaction.setUserId(userId);
        transaction.setTransactionType(TransactionType.ISSUE);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepo.save(transaction);

        return modelMapper.map(updatedBook, BooksDTO.class);
    }

    public BooksDTO returnBook(Long bookId, Long userId) {
        BooksEntity book = booksRepo.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

        TransactionEntity transaction = transactionRepo.findLatestIssuedTransaction(bookId, userId);

        if (transaction == null) {
            throw new IllegalStateException("This book was not issued to the user.");
        }

        book.setQuantity(book.getQuantity() + 1);
        BooksEntity updatedBook = booksRepo.save(book);

        TransactionEntity returnTransaction = new TransactionEntity();
        returnTransaction.setBook(book);
        returnTransaction.setUserId(userId);
        returnTransaction.setTransactionType(TransactionType.RETURN);
        returnTransaction.setTimestamp(LocalDateTime.now());
        transactionRepo.save(returnTransaction);

        return modelMapper.map(updatedBook, BooksDTO.class);
    }

}
