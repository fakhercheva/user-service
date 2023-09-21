package com.exercice.test.controller;

import com.exercice.test.annotation.ApiTracking;
import com.exercice.test.entities.User;
import com.exercice.test.exception.UserNotFoundException;
import com.exercice.test.repository.UserRepository;
import com.exercice.test.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService =userService;
        this.userRepository =userRepository;
    }
    @ApiTracking
    @GetMapping("/details/{id}")
    public ResponseEntity<?> getDetails(@PathVariable Long id){
        try {
            User user = userService.getUserDetails(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @ApiTracking
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully with ID: " + registeredUser.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
