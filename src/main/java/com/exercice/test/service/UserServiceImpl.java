package com.exercice.test.service;

import com.exercice.test.entities.Nationality;
import com.exercice.test.entities.User;
import com.exercice.test.exception.UserNotFoundException;
import com.exercice.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(User user) {

        // Vérification of frensh nationality
        String county = user.getCounty().trim().toLowerCase();
        if (!Nationality.FRENCH.name().toLowerCase().equals(county)) {
            throw new IllegalArgumentException("French resident can only create an account !!");
        }


        //verification of age > 18 for adult
        LocalDate now = LocalDate.now();
        LocalDate birthdate = user.getBirthdate();
        int age = Period.between(birthdate, now).getYears();
        if (age < 18) {
            throw new IllegalArgumentException("Only adult can create an account !!");
        }

            return userRepository.save(user);
    }

    @Override
    public User getUserDetails(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found with ID: " + id));
    }



}
