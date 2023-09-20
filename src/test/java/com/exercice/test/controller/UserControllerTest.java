package com.exercice.test.controller;

import com.exercice.test.controller.UserController;
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

import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userService, null);
    }

    @Test
    public void testGetDetails_UserNotFound() {
        when(userService.getUserDetails(2L)).thenThrow(new UserNotFoundException("user not found !!"));
        ResponseEntity<?> response = userController.getDetails(2L);
        verify(userService, times(1)).getUserDetails(2L);
        assert(response.getStatusCode() == HttpStatus.NOT_FOUND);
        assert(response.getBody().equals("User Not Found"));
    }

    @Test
    public void testRegisterUser_Success() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("test");
        when(userService.register(mockUser)).thenReturn(mockUser);
        ResponseEntity<String> response = userController.registerUser(mockUser);
        verify(userService, times(1)).register(mockUser);
        assert(response.getStatusCode() == HttpStatus.CREATED);
        assert(response.getBody().equals("User registered successfully with ID: 1"));
    }

    @Test
    public void testRegisterUser_Failure() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("test");
        when(userService.register(mockUser)).thenThrow(new IllegalArgumentException("Invalid user data"));
        ResponseEntity<String> response = userController.registerUser(mockUser);
        verify(userService, times(1)).register(mockUser);
        assert(response.getStatusCode() == HttpStatus.BAD_REQUEST);
        assert(response.getBody().equals("Invalid user data"));
    }
}
