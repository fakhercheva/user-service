package com.exercice.test.controller;

import com.exercice.test.annotation.ApiTracking;
import com.exercice.test.dto.UserDTO;
import com.exercice.test.entities.User;
import com.exercice.test.exception.UserNotFoundException;
import com.exercice.test.mapper.UserMapper;
import com.exercice.test.repository.UserRepository;
import com.exercice.test.service.UserService;
import jakarta.validation.Valid;
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
    private final UserMapper userMapper; // Ajoutez le UserMapper
    private final UserRepository userRepository;
    @Autowired
    public UserController(UserService userService,
                          UserRepository userRepository,
                          UserMapper userMapper) {
        this.userService =userService;
        this.userRepository =userRepository;
        this.userMapper = userMapper;
    }
    @ApiTracking
    @GetMapping("/details/{id}")
    public ResponseEntity<?> getDetails(@PathVariable Long id){
        try {
            UserDTO userDTO = userMapper.userToUserDTO(userService.getUserDetails(id)); // Utilisez le UserMapper pour convertir User en UserDTO
            return ResponseEntity.ok(userDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @ApiTracking
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            UserDTO registeredUserDTO = userService.register(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully with ID: " + registeredUserDTO.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
