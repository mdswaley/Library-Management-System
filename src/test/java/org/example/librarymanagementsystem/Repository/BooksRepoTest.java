package org.example.librarymanagementsystem.Repository;

import org.example.librarymanagementsystem.Entity.BooksEntity;
import org.example.librarymanagementsystem.TestContainerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestContainerConfig.class)
class BooksRepoTest {
    @Autowired
    private BooksRepo booksRepo;

    private BooksEntity booksEntity;

    @BeforeEach
    void setUp(){
        booksEntity = BooksEntity.builder()
                .title("Go Set a Watchman")
                .author("Harper Lee")
                .isbn("145323453")
                .AvailableQuantity(30)
                .IssueQuantity(0)
                .build();
    }

    @Test
    void testBook_ifBookNotFoundByTitle_thenReturnEmpty(){
        booksRepo.save(booksEntity);

        Optional<BooksEntity> booksEntities = booksRepo.findBooksEntitiesByTitle("abc");

        assertThat(booksEntities).isEmpty();
    }

    @Test
    void testBook_ifBookFoundByTitle_thenReturnBook(){
        booksRepo.save(booksEntity);

        Optional<BooksEntity> booksEntities = booksRepo.findBooksEntitiesByTitle(booksEntity.getTitle());

        assertThat(booksEntities).isNotNull();
        assertThat(booksEntities).isPresent();
        assertThat(booksEntity.getTitle()).isEqualTo("Go Set a Watchman");
    }


}