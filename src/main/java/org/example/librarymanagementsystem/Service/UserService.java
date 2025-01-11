package org.example.librarymanagementsystem.Service;


import org.example.librarymanagementsystem.DTOs.LoginDTO;
import org.example.librarymanagementsystem.DTOs.SignUpDTO;
import org.example.librarymanagementsystem.DTOs.UserDTO;
import org.example.librarymanagementsystem.Entity.UserEntity;
import org.example.librarymanagementsystem.Exception.ResourceNotFoundException;
import org.example.librarymanagementsystem.Repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
        Set<String> roles = new HashSet<>();
        roles.add("USER");
        user.setRoles(roles);

        return userRepo.save(user);
    }

    public boolean loginUser(LoginDTO userLoginDTO) {
        UserEntity user = userRepo.findByEmail(userLoginDTO.getEmail());
        return user != null && passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword());
    }



    public UserDTO getUserById(Long userId) {
        UserEntity userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        return modelMapper.map(userEntity, UserDTO.class);
    }

    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        UserEntity userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        modelMapper.map(userDTO, userEntity);
        userRepo.save(userEntity);

        return modelMapper.map(userEntity, UserDTO.class);
    }

    public void deleteUser(Long userId) {
        UserEntity userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        userRepo.delete(userEntity);
    }

}
