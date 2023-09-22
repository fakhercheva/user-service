package com.exercice.test.dto;

import com.exercice.test.entities.User;
import jakarta.persistence.Entity;
import lombok.*;


import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private LocalDate birthdate;
    private String county;
    private String phone;
    private String gender;
}
