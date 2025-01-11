package org.example.librarymanagementsystem.Controller;

import org.example.librarymanagementsystem.DTOs.LoginDTO;
import org.example.librarymanagementsystem.DTOs.SignUpDTO;
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

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody SignUpDTO userSignUpDTO) {
        try {
            userService.signUpUser(userSignUpDTO);
            return new ResponseEntity<>("User registered successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO userLoginDTO) {
        boolean isAuthenticated = userService.loginUser(userLoginDTO);
        if (isAuthenticated) {
            return new ResponseEntity<>("Login successful.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid email or password.", HttpStatus.UNAUTHORIZED);
        }
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
