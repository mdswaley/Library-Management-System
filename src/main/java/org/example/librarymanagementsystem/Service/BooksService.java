package org.example.librarymanagementsystem.Service;

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
public class BooksService {
    private final BooksRepo booksRepo;
    private final ModelMapper modelMapper;

    public BooksService(BooksRepo booksRepo, ModelMapper modelMapper) {
        this.booksRepo = booksRepo;
        this.modelMapper = modelMapper;
    }

    public BooksDTO addBook(BooksDTO booksDTO){
        BooksEntity booksEntity = modelMapper.map(booksDTO,BooksEntity.class);
        booksRepo.save(booksEntity);
        return modelMapper.map(booksEntity,BooksDTO.class);
    }

    public void isExistsByBookId(Long bookId) {
        boolean exists = booksRepo.existsById(bookId);
        if(!exists) throw new ResourceNotFoundException("Book not found with id: "+bookId);
    }

    public BooksDTO updateBook(Long bookId,BooksDTO booksDTO){
        isExistsByBookId(bookId);
        BooksEntity booksEntity = modelMapper.map(booksDTO, BooksEntity.class);
        booksEntity.setId(bookId);
        BooksEntity saveBook = booksRepo.save(booksEntity);
        return modelMapper.map(saveBook, BooksDTO.class);
    }



    public Optional<BooksDTO> getBooks(Long id){
        return booksRepo.findById(id).map(booksEntity -> modelMapper.map(booksEntity, BooksDTO.class));
    }

    public List<BooksDTO> getAllBooks() {
        List<BooksEntity> booksEntities = booksRepo.findAll();
        return booksEntities
                .stream()
                .map(booksEntity1 -> modelMapper.map(booksEntity1, BooksDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteBook(Long id) {
        isExistsByBookId(id);
        booksRepo.deleteById(id);
    }
}
