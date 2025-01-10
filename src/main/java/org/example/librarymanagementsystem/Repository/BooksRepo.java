package org.example.librarymanagementsystem.Repository;

import org.example.librarymanagementsystem.Entity.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepo extends JpaRepository<BooksEntity,Long> {
}
