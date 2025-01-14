package org.example.librarymanagementsystem.Controller;

import org.example.librarymanagementsystem.DTOs.BooksDTO;
import org.example.librarymanagementsystem.DTOs.TransactionDTO;
import org.example.librarymanagementsystem.Exception.ResourceNotFoundException;
import org.example.librarymanagementsystem.Service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/issue/{bookId}/{userId}")
    public ResponseEntity<TransactionDTO> issueBook(@PathVariable Long bookId, @PathVariable Long userId) {
        try {
            TransactionDTO transactionDTO = transactionService.issueBook(bookId, userId);
            return new ResponseEntity<>(transactionDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/return/{bookId}/{userId}")
    public ResponseEntity<TransactionDTO> returnBook(@PathVariable Long bookId, @PathVariable Long userId) {
        try {
            TransactionDTO transactionDTO = transactionService.returnBook(bookId, userId);
            return new ResponseEntity<>(transactionDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
