package org.example.librarymanagementsystem.DTOs;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.example.librarymanagementsystem.Entity.BooksEntity;
import org.example.librarymanagementsystem.Entity.UserEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;
    private BooksEntity book;
    private UserEntity user;
    private LocalDateTime issueDate;
    private LocalDateTime returnData;
    private Double fine;
}
