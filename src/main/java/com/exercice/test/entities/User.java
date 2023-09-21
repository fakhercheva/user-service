package com.exercice.test.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "app_user")
@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Builder

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate birthdate;
    private String county;
    private String phone;
    private String gender;

}
