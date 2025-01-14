package org.example.librarymanagementsystem.DTOs;

import jakarta.persistence.*;
import lombok.*;
import org.example.librarymanagementsystem.Entity.Enum.Roles;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Set<Roles> roles;
}
