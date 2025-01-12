package org.example.librarymanagementsystem.Controller;

import org.example.librarymanagementsystem.DTOs.BooksDTO;
import org.example.librarymanagementsystem.Exception.ResourceNotFoundException;
import org.example.librarymanagementsystem.Service.BooksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Books")
@CrossOrigin(origins = "http://localhost:3000")
public class BooksController {
    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @PostMapping("/addBook")
    public ResponseEntity<BooksDTO> addBook(@RequestBody BooksDTO booksDTO){
        return new ResponseEntity<>(booksService.addBook(booksDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BooksDTO> getById(@PathVariable Long bookId){
        Optional<BooksDTO> booksDTO = booksService.getBooks(bookId);
        return booksDTO
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: "+bookId));
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BooksDTO> updateBook(@RequestBody BooksDTO booksDTO, @PathVariable Long bookId) {
        return ResponseEntity.ok(booksService.updateBook(bookId, booksDTO));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Long bookId) {
        booksService.deleteBook(bookId);
        return ResponseEntity.ok("Book deleted successfully with ID: " + bookId);
    }

    @GetMapping
    public ResponseEntity<List<BooksDTO>> getAllBooks() {
        return ResponseEntity.ok(booksService.getAllBooks());
    }
}
