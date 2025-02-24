package org.example.librarymanagementsystem.Controller;

import org.example.librarymanagementsystem.DTOs.BooksDTO;
import org.example.librarymanagementsystem.Entity.BooksEntity;
import org.example.librarymanagementsystem.Repository.BooksRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BooksControllerTest extends AbstractIntegrationTest{

    @Autowired
    private BooksRepo booksRepo;

    @BeforeEach
    void setUp(){
        booksRepo.deleteAll();
    }

//    Get book by id
    @Test
    void testGetBookById_whenIdIsNotExist_thenThrowException(){
        webTestClient.get()
                .uri("/Books/{id}",testBookDto.getId())
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void testGetBookById_whenIdPresent_thenReturnBook(){
        BooksEntity saveBook = booksRepo.save(testBookClass);

        webTestClient.get()
                .uri("/Books/{id}",saveBook.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BooksDTO.class)
                .value(booksDTO -> {
                    assertThat(booksDTO.getTitle()).isEqualTo(saveBook.getTitle());
                    assertThat(booksDTO.getAuthor()).isEqualTo(saveBook.getAuthor());
                });
    }

//    Get All Books
    @Test
    void testGetAllBooks_whenBookIsNotPresent_thenReturnNull(){
        webTestClient.get()
                .uri("/Books")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(List.class);
    }

    @Test
    void testGetAllBook_whenBooksArePresent_thenReturnBooks(){
        BooksEntity save = booksRepo.save(testBookClass);
        webTestClient.get()
                .uri("/Books")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(List.class);
    }

//    Get Book by Title
    @Test
    void testGetBookByTitle_whenTitleIsNotPresent_thenThrowException(){
        webTestClient.get()
                .uri("/Books/title/abc")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void testGetBookByTitle_whenTitleIsPresent_thenReturnBook(){
        BooksEntity save = booksRepo.save(testBookClass);

        webTestClient.get()
                .uri("/Books/title/{title}",save.getTitle())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo(testBookDto.getTitle());
    }

//    Get book by author
    @Test
    void testGetBookByAuthor_whenAuthorIsNotPresent_thenReturnEmptyList(){
        webTestClient.get()
                .uri("/Books/author/xyz")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(List.class);
    }

    @Test
    void testGetBookByAuthor_whenAuthorIsPresent_thenReturnList(){
        BooksEntity save = booksRepo.save(testBookClass);

        webTestClient.get()
                .uri("/Books/author/{author}",save.getAuthor())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(List.class);
    }

//    Add new Book
    @Test
    void testAddBook_whenBookIsExistByTitle_thenThrowException(){
        BooksEntity saveBook = booksRepo.save(testBookClass);

        webTestClient.post()
                .uri("/Books/addBook")
                .bodyValue(testBookDto)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Test
    void testAddBook_whenBookIsNotExistByTitle_thenCreateNewBook(){
        webTestClient.post()
                .uri("/Books/addBook")
                .bodyValue(testBookClass)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.title").isEqualTo(testBookDto.getTitle());
    }

//    Update Book By id
    @Test
    void testUpdateBook_whenBookIsNotPresent_thenThrowException(){
        webTestClient.put()
                .uri("/Books/999")
                .bodyValue(testBookDto)
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(BooksDTO.class);
    }

    @Test
    void testUpdateBook_whenBookIsPresent_thenUpdate(){
        BooksEntity save = booksRepo.save(testBookClass);
        System.out.println(save.getId());
        testBookClass.setTitle("Revolution 2020");
        testBookClass.setAvailableQuantity(40);

        webTestClient.put()
                .uri("/Books/{id}",save.getId())
                .bodyValue(testBookClass)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BooksDTO.class);
    }


//    Delete Book by id
    @Test
    void testDeleteBook_whenBookIsNotPresent_thenThrowException(){
        webTestClient.delete()
                .uri("/Books/999")
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(String.class);
    }

    @Test
    void testDeleteBook_whenBookIsPresent_thenDeleteBook(){
        BooksEntity save = booksRepo.save(testBookClass);

        webTestClient.delete()
                .uri("/Books/{id}",save.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class);

        webTestClient.delete()
                .uri("/Books/{id}",save.getId())
                .exchange()
                .expectStatus()
                .isNotFound();
    }











}