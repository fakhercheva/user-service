package com.exercice.test.service;

import com.exercice.test.entities.User;

public interface UserService {
    User register(User user);
    User getUserDetails(Long id);

}
