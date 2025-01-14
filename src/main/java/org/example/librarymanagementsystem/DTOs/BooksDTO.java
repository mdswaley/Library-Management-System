package org.example.librarymanagementsystem.DTOs;

//import jakarta.persistence.Column;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BooksDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer IssueQuantity;
    private Integer AvailableQuantity;

}
