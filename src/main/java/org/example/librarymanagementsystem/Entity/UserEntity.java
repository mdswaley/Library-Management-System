package org.example.librarymanagementsystem.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.librarymanagementsystem.Entity.Enum.Roles;
import org.example.librarymanagementsystem.Validation.UserEmail;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true,nullable = false)
    @UserEmail
    private String email;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles roles;
}
