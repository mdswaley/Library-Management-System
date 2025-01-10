package org.example.librarymanagementsystem.Controller;

import org.example.librarymanagementsystem.DTOs.UserDTO;
import org.example.librarymanagementsystem.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO){
        return new ResponseEntity<>(userService.addUser(userDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long userId){
        UserDTO userDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId,@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.updateUser(userId,userDTO));
    }


    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok("User with id "+userId+" is deleted.");
    }





}
