package org.example.librarymanagementsystem.Controller;

import org.example.librarymanagementsystem.DTOs.BooksDTO;
import org.example.librarymanagementsystem.Service.BooksService;
import org.example.librarymanagementsystem.Service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/issue/{bookId}/{userId}")
    public ResponseEntity<BooksDTO> issueBook(@PathVariable Long bookId, @PathVariable Long userId) {
        try {
            BooksDTO booksDTO = transactionService.issueBook(bookId, userId);
            return new ResponseEntity<>(booksDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/return/{bookId}/{userId}")
    public ResponseEntity<BooksDTO> returnBook(@PathVariable Long bookId, @PathVariable Long userId) {
        try {
            BooksDTO booksDTO = transactionService.returnBook(bookId, userId);
            return new ResponseEntity<>(booksDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
