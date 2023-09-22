package com.exercice.test.service;

import com.exercice.test.dto.UserDTO;
import com.exercice.test.entities.Nationality;
import com.exercice.test.entities.User;
import com.exercice.test.exception.UserNotFoundException;
import com.exercice.test.mapper.UserMapper;
import com.exercice.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserRepository userRepository
            ,UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO register(UserDTO userDTO) {
        if (userDTO.getName() == null || userDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("The 'name' field cannot be null or empty.");
        }
        if (userDTO.getBirthdate() == null) {
            throw new IllegalArgumentException("The 'Birthdate' field cannot be null or empty.");
        }
        if (userDTO.getCounty() == null || userDTO.getCounty().isEmpty()) {
            throw new IllegalArgumentException("The 'County' field cannot be null or empty.");
        }

        // Vérification of frensh nationality
        String county = userDTO.getCounty().trim().toLowerCase();
        if (!Nationality.FRENCH.name().toLowerCase().equals(county)) {
            throw new IllegalArgumentException("French resident can only create an account !!");
        }

        //verification of age > 18 for adult
        LocalDate now = LocalDate.now();
        LocalDate birthdate = userDTO.getBirthdate();
        int age = Period.between(birthdate, now).getYears();
        if (age < 18) {
            throw new IllegalArgumentException("Only adult can create an account !!");
        }

        User user = userMapper.userDTOToUser(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public User getUserDetails(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found with ID: " + id));
    }

}
