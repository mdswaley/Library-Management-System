package org.example.librarymanagementsystem.DTOs;

//import jakarta.persistence.Column;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.librarymanagementsystem.Validation.ValidISBN;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BooksDTO {
    private Long id;

    @NotBlank(message = "Title cannot be empty or null")
    private String title;

    @NotBlank(message = "Author cannot be empty or null")
    private String author;

    @ValidISBN
    private String isbn;

    @Min(value = 0, message = "Issue Quantity cannot be negative")
    private Integer IssueQuantity;

    @Min(value = 0, message = "Available Quantity cannot be negative")
    private Integer AvailableQuantity;


}
