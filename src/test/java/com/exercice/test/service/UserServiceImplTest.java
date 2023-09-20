package com.exercice.test.service;

import com.exercice.test.entities.User;
import com.exercice.test.exception.UserNotFoundException;
import com.exercice.test.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    void testGetUserDetailsExistingUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        User returnedUser = userService.getUserDetails(userId);
        assertEquals(user, returnedUser);
    }


    @Test
    void testGetUserDetailsUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        Long nonExistingUserId = 999L;
        assertThrows(UserNotFoundException.class, () -> userService.getUserDetails(nonExistingUserId));
    }

    @Test
    void testRegisterValidUser() {
        User validUser = new User();
        validUser.setCounty("FRENCH");
        validUser.setBirthdate(LocalDate.of(1990, 1, 1));
        when(userRepository.save(validUser)).thenReturn(validUser);
        User registeredUser = userService.register(validUser);
        assertEquals(validUser, registeredUser);
    }

    @Test
    void testRegisterInvalidUserNationality() {
        User invalidUser = new User();
        invalidUser.setCounty("GERMAN");
        invalidUser.setBirthdate(LocalDate.of(1990, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> userService.register(invalidUser));
    }

    @Test
    void testRegisterInvalidUserAge() {
        User invalidUser = new User();
        invalidUser.setCounty("FRENCH");
        invalidUser.setBirthdate(LocalDate.now().minusYears(17));
        assertThrows(IllegalArgumentException.class, () -> userService.register(invalidUser));
    }



}
