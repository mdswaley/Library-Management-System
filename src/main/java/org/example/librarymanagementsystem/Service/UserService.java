package org.example.librarymanagementsystem.Service;


import org.example.librarymanagementsystem.DTOs.LoginDTO;
import org.example.librarymanagementsystem.DTOs.SignUpDTO;
import org.example.librarymanagementsystem.DTOs.UserDTO;
import org.example.librarymanagementsystem.Entity.Enum.Roles;
import org.example.librarymanagementsystem.Entity.UserEntity;
import org.example.librarymanagementsystem.Exception.ResourceNotFoundException;
import org.example.librarymanagementsystem.Repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepo userRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    public UserEntity signUpUser(SignUpDTO userSignUpDTO) {
        if (userRepo.existsByEmail(userSignUpDTO.getEmail())) {
            throw new IllegalArgumentException("Email already in use.");
        }

        UserEntity user = new UserEntity();
        user.setEmail(userSignUpDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userSignUpDTO.getPassword()));
        user.setName(userSignUpDTO.getName());
        user.setRoles(Roles.USER);

        return userRepo.save(user);
    }

    public boolean loginUser(LoginDTO userLoginDTO) {
        UserEntity user = userRepo.findByEmail(userLoginDTO.getEmail()).orElse(null);
        return user != null && passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword());
    }

    public void ensureAdminExists() {
        String adminEmail = "raja@gmail.com";
        if (!userRepo.findByEmail(adminEmail).isPresent()) {
            UserEntity admin = new UserEntity();
            admin.setName("Admin");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("1234"));
            admin.setRoles(Roles.ADMIN);
            userRepo.save(admin);
        }
    }

    public List<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }

    public void updateUserRole(Long userId, Roles newRole) {
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setRoles(newRole);
        userRepo.save(user);
    }



    public UserDTO getUserById(Long userId) {
        UserEntity userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        return modelMapper.map(userEntity, UserDTO.class);
    }


    public void deleteUser(Long userId) {
        UserEntity userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        userRepo.delete(userEntity);
    }

    public UserEntity findByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
}
