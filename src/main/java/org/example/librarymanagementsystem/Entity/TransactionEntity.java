package org.example.librarymanagementsystem.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.librarymanagementsystem.Entity.Enum.TransactionType;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "book_id", nullable = false)
    private BooksEntity book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private LocalDateTime issueDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    private Double fine;

    private LocalDateTime timestamp;
}
