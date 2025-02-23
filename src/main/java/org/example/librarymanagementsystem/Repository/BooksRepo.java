package org.example.librarymanagementsystem.Repository;

import org.example.librarymanagementsystem.DTOs.BooksDTO;
import org.example.librarymanagementsystem.Entity.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepo extends JpaRepository<BooksEntity,Long> {
    Optional<BooksEntity> findBooksEntitiesByTitle(String title);
    List<BooksEntity> findAllByAuthor(String author);
}
