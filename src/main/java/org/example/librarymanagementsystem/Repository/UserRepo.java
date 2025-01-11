package org.example.librarymanagementsystem.Repository;

import org.example.librarymanagementsystem.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,Long> {
    boolean existsByEmail(String email);
    UserEntity findByEmail(String email);
}
