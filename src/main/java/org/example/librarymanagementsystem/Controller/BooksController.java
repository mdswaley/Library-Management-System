package org.example.librarymanagementsystem.Controller;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BooksController {
    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @PostMapping("/addBook")
    public ResponseEntity<BooksDTO> addBook(@RequestBody BooksDTO booksDTO){
        log.info("Adding a new book with title: {}", booksDTO.getTitle());
        return new ResponseEntity<>(booksService.addBook(booksDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BooksDTO> getById(@PathVariable Long bookId){
        log.info("Fetching book with ID: {}", bookId);
        return ResponseEntity.ok(booksService.getBooks(bookId));
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BooksDTO> updateBook(@RequestBody BooksDTO booksDTO, @PathVariable Long bookId) {
        log.info("Updating book with ID: {}", bookId);
        return ResponseEntity.ok(booksService.updateBook(bookId, booksDTO));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Long bookId) {
        log.info("Deleting book with ID: {}", bookId);
        booksService.deleteBook(bookId);
        return ResponseEntity.ok("Book deleted successfully with ID: " + bookId);
    }

    @GetMapping
    public ResponseEntity<List<BooksDTO>> getAllBooks() {
        log.info("Fetching all books.");
        return ResponseEntity.ok(booksService.getAllBooks());
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<BooksDTO> getBookByTitle(@PathVariable String title){
        log.info("Fetching book with title: {}", title);
        Optional<BooksDTO> booksDTO = booksService.getBookByTitle(title);
        return booksDTO.map(ResponseEntity::ok)
                .orElseThrow(() -> {
                    log.error("Book not found with title: {}", title);
                    return new ResourceNotFoundException("Book is not present with title: " + title);
                });
    }

    @GetMapping("/author/{authorName}")
    public ResponseEntity<List<BooksDTO>> getBookByAuthorName(@PathVariable String authorName){
        log.info("Fetching books by author: {}", authorName);
        List<BooksDTO> booksDTO = booksService.getAllBookByAuthor(authorName);
        return ResponseEntity.ok(booksDTO);
    }
}