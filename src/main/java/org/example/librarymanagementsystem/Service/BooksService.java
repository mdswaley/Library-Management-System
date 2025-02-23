package org.example.librarymanagementsystem.Service;

import lombok.extern.slf4j.Slf4j;
import org.example.librarymanagementsystem.DTOs.BooksDTO;
import org.example.librarymanagementsystem.Entity.BooksEntity;
import org.example.librarymanagementsystem.Exception.ResourceNotFoundException;
import org.example.librarymanagementsystem.Repository.BooksRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BooksService {
    private final BooksRepo booksRepo;
    private final ModelMapper modelMapper;

    public BooksService(BooksRepo booksRepo, ModelMapper modelMapper) {
        this.booksRepo = booksRepo;
        this.modelMapper = modelMapper;
    }

    public BooksDTO addBook(BooksDTO booksDTO){
        log.info("Attempting to create a new book: {}", booksDTO);
        BooksEntity booksEntity = modelMapper.map(booksDTO, BooksEntity.class);
        Optional<BooksEntity> isExistByTitle = booksRepo.findBooksEntitiesByTitle(booksDTO.getTitle());

        if(isExistByTitle.isPresent()){
            log.error("Book already exists with title: {}", booksDTO.getTitle());
            throw new RuntimeException("Book already exists.");
        }

        booksEntity = booksRepo.save(booksEntity);
        log.info("Successfully created new book with id: {}", booksEntity.getId());
        return modelMapper.map(booksEntity, BooksDTO.class);
    }

    public void isExistsByBookId(Long bookId) {
        log.info("Checking existence of book with id: {}", bookId);
        boolean exists = booksRepo.existsById(bookId);
        if(!exists) {
            log.error("Book not found with id: {}", bookId);
            throw new ResourceNotFoundException("Book not found with id: " + bookId);
        }
    }

    public BooksDTO updateBook(Long bookId, BooksDTO booksDTO){
        log.info("Updating book with id: {}, new details: {}", bookId, booksDTO);
        isExistsByBookId(bookId);
        BooksEntity booksEntity = modelMapper.map(booksDTO, BooksEntity.class);
        booksEntity.setId(bookId);
        BooksEntity updatedBook = booksRepo.save(booksEntity);
        log.info("Successfully updated book with id: {}", bookId);
        return modelMapper.map(updatedBook, BooksDTO.class);
    }

    public BooksDTO getBooks(Long id){
        log.info("Fetching book by id: {}", id);
        BooksEntity booksEntity = booksRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Book with id {} not found", id);
                    throw new ResourceNotFoundException("Book not found");
                });
        log.info("Successfully retrieved book: {}", booksEntity);
        return modelMapper.map(booksEntity, BooksDTO.class);
    }

    public List<BooksDTO> getAllBooks() {
        log.info("Fetching all books.");
        List<BooksEntity> booksEntities = booksRepo.findAll();
        log.info("Total books found: {}", booksEntities.size());
        return booksEntities.stream()
                .map(book -> modelMapper.map(book, BooksDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteBook(Long id) {
        log.info("Attempting to delete book with id: {}", id);
        isExistsByBookId(id);
        booksRepo.deleteById(id);
        log.info("Successfully deleted book with id: {}", id);
    }

    public Optional<BooksDTO> getBookByTitle(String title){
        log.info("Fetching book by title: {}", title);
        Optional<BooksEntity> isExistBook = booksRepo.findBooksEntitiesByTitle(title);
        if (isExistBook.isPresent()) {
            log.info("Book found: {}", isExistBook.get());
        } else {
            log.warn("No book found with title: {}", title);
        }
        return isExistBook.map(book -> modelMapper.map(book, BooksDTO.class));
    }

    public List<BooksDTO> getAllBookByAuthor(String author){
        log.info("Fetching all books by author: {}", author);
        List<BooksEntity> booksEntities = booksRepo.findAllByAuthor(author);
        log.info("Total books found for author {}: {}", author, booksEntities.size());
        return booksEntities.stream()
                .map(book -> modelMapper.map(book, BooksDTO.class))
                .collect(Collectors.toList());
    }
}
