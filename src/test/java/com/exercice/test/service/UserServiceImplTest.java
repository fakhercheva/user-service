package com.exercice.test.service;

import com.exercice.test.dto.UserDTO;
import com.exercice.test.entities.Nationality;
import com.exercice.test.entities.User;
import com.exercice.test.exception.UserNotFoundException;
import com.exercice.test.mapper.UserMapper;
import com.exercice.test.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterUser_Success() {
        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setName("test");
        mockUserDTO.setBirthdate(LocalDate.of(1990, 1, 1));
        mockUserDTO.setCounty(Nationality.FRENCH.name());

        User mockUser = new User();
        mockUser.setName(mockUserDTO.getName());
        mockUser.setBirthdate(mockUserDTO.getBirthdate());
        mockUser.setCounty(mockUserDTO.getCounty());

        when(userMapper.userDTOToUser(mockUserDTO)).thenReturn(mockUser);
        when(userRepository.save(mockUser)).thenReturn(mockUser);
        when(userMapper.userToUserDTO(mockUser)).thenReturn(mockUserDTO);

        UserDTO registeredUserDTO = userService.register(mockUserDTO);

        assertNotNull(registeredUserDTO);
        assertEquals(mockUserDTO.getName(), registeredUserDTO.getName());
        assertEquals(mockUserDTO.getBirthdate(), registeredUserDTO.getBirthdate());
        assertEquals(mockUserDTO.getCounty(), registeredUserDTO.getCounty());
    }

    @Test
    public void testRegisterUser_InvalidName() {
        UserDTO mockUserDTO = new UserDTO();
        mockUserDTO.setBirthdate(LocalDate.of(1990, 1, 1));
        mockUserDTO.setCounty(Nationality.FRENCH.name());

        assertThrows(IllegalArgumentException.class, () -> userService.register(mockUserDTO));
    }

    @Test
    public void testGetUserDetails_ExistingUser() {
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setName("test");
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        User user = userService.getUserDetails(userId);

        assertNotNull(user);
        assertEquals(userId, user.getId());
        assertEquals(mockUser.getName(), user.getName());
    }

    @Test
    public void testGetUserDetails_NonExistingUser() {
        Long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserDetails(userId));
    }
}
