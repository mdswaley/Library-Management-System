package org.example.librarymanagementsystem.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.librarymanagementsystem.Entity.Enum.Roles;


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
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Roles roles;
}
