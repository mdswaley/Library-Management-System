package org.example.librarymanagementsystem.Service;

import org.example.librarymanagementsystem.DTOs.BooksDTO;
import org.example.librarymanagementsystem.DTOs.UserDTO;
import org.example.librarymanagementsystem.Entity.BooksEntity;
import org.example.librarymanagementsystem.Entity.UserEntity;
import org.example.librarymanagementsystem.Exception.ResourceNotFoundException;
import org.example.librarymanagementsystem.Repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    public UserService(UserRepo userRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    public UserDTO addUser(UserDTO userDTO){
        UserEntity user = modelMapper.map(userDTO,UserEntity.class);
        userRepo.save(user);
        return modelMapper.map(user,UserDTO.class);
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
