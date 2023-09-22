package com.exercice.test.controller;

import com.exercice.test.dto.UserDTO;
import com.exercice.test.entities.User;
import com.exercice.test.exception.UserNotFoundException;
import com.exercice.test.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userService, null,null);
    }

    @Test
    public void testRegisterUser_Success() {
        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setId(1L);
        mockUserDTO.setName("test");
        when(userService.register(mockUserDTO)).thenReturn(mockUserDTO);
        ResponseEntity<?> response = userController.registerUser(mockUserDTO);
        verify(userService, times(1)).register(mockUserDTO);
        assert(response.getStatusCode() == HttpStatus.CREATED);
        assert(response.getBody().equals("User registered successfully"));
    }

    @Test
    public void testRegisterUser_Failure() {
        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setId(1L);
        mockUserDTO.setName("test");
        when(userService.register(mockUserDTO)).thenThrow(new IllegalArgumentException("Invalid user data"));
        ResponseEntity<?> response = userController.registerUser(mockUserDTO);
        verify(userService, times(1)).register(mockUserDTO);
        assert(response.getStatusCode() == HttpStatus.BAD_REQUEST);
        assert(response.getBody().equals("Invalid user data"));
    }

    @Test
    public void testGetDetailsWithExistingUser() {
        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setId(1L);
        mockUserDTO.setName("test");
        User mockUser = new User();
        mockUser.setId(mockUserDTO.getId());
        mockUser.setName(mockUserDTO.getName());
        when(userService.getUserDetails(1L)).thenReturn(mockUser);
        ResponseEntity<?> response = userController.getDetails(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUserDTO, response.getBody());
    }


    @Test
    public void testGetDetailsWithNonExistingUser() {
        when(userService.getUserDetails(2L)).thenThrow(new UserNotFoundException("User Not Found"));
        ResponseEntity<?> response = userController.getDetails(2L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
