package org.example.librarymanagementsystem.Service;

import org.example.librarymanagementsystem.DTOs.BooksDTO;
import org.example.librarymanagementsystem.Entity.BooksEntity;
import org.example.librarymanagementsystem.Exception.ResourceNotFoundException;
import org.example.librarymanagementsystem.Repository.BooksRepo;
import org.example.librarymanagementsystem.TestContainerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Import(TestContainerConfig.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BooksServiceTest {
    @InjectMocks
    private BooksService booksService;

    @Mock
    private BooksRepo booksRepo;

    @Mock
    private BooksEntity mockBooks;

    @Mock
    private BooksDTO mockBooksDTO;

    @Spy
    private ModelMapper modelMapper;


    @BeforeEach
    void setUp(){
        mockBooks = BooksEntity.builder()
                .title("Go Set a Watchman")
                .author("Harper Lee")
                .isbn("145323453")
                .AvailableQuantity(30)
                .IssueQuantity(0)
                .build();

        mockBooksDTO = modelMapper.map(mockBooks,BooksDTO.class);
        mockBooksDTO.setId(1L);
    }


//    Create new Book
    @Test
    void testAddBook_whenBookIsAlreadyPresent_thenReturnException(){
        when(booksRepo.findBooksEntitiesByTitle(mockBooks.getTitle())).thenReturn(Optional.of(mockBooks));

        assertThatThrownBy(()->booksService.addBook(mockBooksDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Book already exists.");
    }

    @Test
    void testAddBook_whenBookIsNotPresent_thenCreateBook(){
        when(booksRepo.findBooksEntitiesByTitle(mockBooks.getTitle())).thenReturn(Optional.empty());
        when(booksRepo.save(any(BooksEntity.class))).thenReturn(mockBooks);

        BooksDTO booksDTO = booksService.addBook(mockBooksDTO);
        assertThat(booksDTO).isNotNull();
        assertThat(booksDTO.getTitle()).isEqualTo(mockBooks.getTitle());
        verify(booksRepo).save(any(BooksEntity.class));
    }

//    Get book by id
    @Test
    void testGetBookById_whenBookIsNotPresent_thenThrowException(){
        when(booksRepo.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(()->booksService.getBooks(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Book not found with id : "+mockBooksDTO.getId());

        verify(booksRepo,never()).save(any(BooksEntity.class));
    }

    @Test
    void testGetBookById_whenBookIsPresent_thenReturnBook(){
        when(booksRepo.findById(1L)).thenReturn(Optional.of(mockBooks));

        BooksDTO book = booksService.getBooks(1L);

        assertThat(book).isNotNull();
        assertThat(book.getId()).isEqualTo(mockBooks.getId());

        verify(booksRepo).findById(1L);
    }

//    is book exist or not
    @Test
    void testIsExist_whenBookIsNotExistById_thenThrowException(){
        when(booksRepo.existsById(1L)).thenReturn(false);

        assertThatThrownBy(()->booksService.isExistsByBookId(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Book not found with id: 1");
    }

    @Test
    void testIsExist_whenBookIsPresent_thenReturnBook(){
        when(booksRepo.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> booksService.isExistsByBookId(1L));
        verify(booksRepo).existsById(1L);
    }

//    Get all book
    @Test
    void testGetAllBook_whenBookIsPresent_thenReturn(){
        when(booksRepo.findAll()).thenReturn(List.of(mockBooks));

        List<BooksDTO> book = booksService.getAllBooks();

        assertThat(book.size()).isEqualTo(1);

        verify(booksRepo).findAll();
    }

//    Get book by title
    @Test
    void testGetBookByTitle_whenBookIsNotPresentByTitle_thenThrowException(){
        String title = "abcd";
        when(booksRepo.findBooksEntitiesByTitle(title)).thenReturn(Optional.empty());

        assertThatThrownBy(()->booksService.getBookByTitle(title))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Book not found by title : "+title);
    }

    @Test
    void testGetBookByTitle_whenBookIsPresentByTitle_thenReturnBook(){
        when(booksRepo.findBooksEntitiesByTitle(mockBooks.getTitle())).thenReturn(Optional.of(mockBooks));

        Optional<BooksDTO> saveBook = booksService.getBookByTitle(mockBooksDTO.getTitle());

        assertThat(saveBook).isNotNull();
        assertThat(saveBook).isNotEmpty();
        assertThat(saveBook.get().getTitle()).isEqualTo(mockBooksDTO.getTitle());
    }

    //    Get All book By Author
    @Test
    void testGetBookByAuthor_whenBookIsPresentByAuthor_thenReturnBook(){

            when(booksRepo.findAllByAuthor(mockBooksDTO.getAuthor())).thenReturn(List.of(mockBooks));

            List<BooksDTO> getAllByAuthor = booksService.getAllBookByAuthor(mockBooksDTO.getAuthor());

            assertThat(getAllByAuthor.size()).isEqualTo(1);
            assertThat(getAllByAuthor.get(0).getAuthor()).isEqualTo(mockBooksDTO.getAuthor());
    }

//    Update Book
    @Test
    void testUpdateBook_whenBookIsNotExist_thenThrowException(){
        when(booksRepo.existsById(1L)).thenReturn(false);

        assertThatThrownBy(()->booksService.updateBook(1L,mockBooksDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Book not found with id: 1");
    }

    @Test
    void testUpdateBook_whenBookIsExist_thenUpdateBook(){
        when(booksRepo.existsById(1L)).thenReturn(true);
        mockBooksDTO.setTitle("xyz");
        mockBooksDTO.setIssueQuantity(2);
        mockBooksDTO.setIsbn("47297372954");

        BooksEntity newBook = modelMapper.map(mockBooksDTO,BooksEntity.class);
        when(booksRepo.save(any(BooksEntity.class))).thenReturn(newBook);

        BooksDTO updateBook = booksService.updateBook(mockBooksDTO.getId(),mockBooksDTO);

        assertThat(updateBook).isEqualTo(mockBooksDTO);

        verify(booksRepo).existsById(1L);
        verify(booksRepo).save(any());
    }


//    Delete by id
    @Test
    void testDeleteBook_whenBookIsNotExist_thenThrowException(){
        when(booksRepo.existsById(1L)).thenReturn(false);

        assertThatThrownBy(()->booksService.deleteBook(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Book not found with id: 1");
    }

    @Test
    void testDeleteBook_whenBookIsPresent_thenDeleteBook(){
        when(booksRepo.existsById(1L)).thenReturn(true);

        assertThatCode(()->booksService.deleteBook(1L))
                .doesNotThrowAnyException();

        verify(booksRepo).existsById(1L);
        verify(booksRepo).deleteById(1L);
    }

}