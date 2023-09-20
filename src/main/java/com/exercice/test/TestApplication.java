package com.exercice.test;

import com.exercice.test.entities.Nationality;
import com.exercice.test.entities.User;
import com.exercice.test.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Bean
    CommandLineRunner start (UserRepository userRepository) {
        LocalDate localDate = LocalDate.of(2005, 9, 20);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return args -> {
            userRepository.save(User.builder()
                    .name("fakher")
                    .birthdate(date)
                    .county(String.valueOf(Nationality.FRENCH))
                    .phone("55768381")
                    .gender("Homme")
                    .build()
            );
        };
    }

}
