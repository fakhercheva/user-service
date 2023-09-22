package com.exercice.test.service;

import com.exercice.test.dto.UserDTO;
import com.exercice.test.entities.User;

public interface UserService {
    UserDTO register(UserDTO userDTO);
    User getUserDetails(Long id);

}
