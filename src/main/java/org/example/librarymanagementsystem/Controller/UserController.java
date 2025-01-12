package org.example.librarymanagementsystem.Controller;

import org.example.librarymanagementsystem.DTOs.LoginDTO;
import org.example.librarymanagementsystem.DTOs.SignUpDTO;
import org.example.librarymanagementsystem.DTOs.UserDTO;
import org.example.librarymanagementsystem.Entity.Enum.Roles;
import org.example.librarymanagementsystem.Entity.UserEntity;
import org.example.librarymanagementsystem.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO userLoginDTO) {
        boolean isAuthenticated = userService.loginUser(userLoginDTO);
        if (isAuthenticated) {
            UserEntity user = userService.findByEmail(userLoginDTO.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful.");
            response.put("roles", user.getRoles());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("message", "Invalid email or password."), HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/init-admin")
    public String ensureAdminExists() {
        userService.ensureAdminExists();
        return "Admin initialized!";
    }

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}/role")
    public String updateUserRole(@PathVariable Long id, @RequestParam Roles newRole) {
        userService.updateUserRole(id, newRole);
        return "User role updated!";
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long userId){
        UserDTO userDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userDTO);
    }



    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok("User with id "+userId+" is deleted.");
    }





}
