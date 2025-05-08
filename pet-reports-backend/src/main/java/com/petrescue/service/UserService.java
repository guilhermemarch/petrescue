package com.petrescue.service;

import com.petrescue.model.User;
import java.util.UUID;

public interface UserService {
    User createUser(User user);
    User getUserById(UUID id);
    User getUserByEmail(String email);
    boolean existsByEmail(String email);
} 