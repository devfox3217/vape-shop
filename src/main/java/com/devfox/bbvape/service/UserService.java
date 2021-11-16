package com.devfox.bbvape.service;

import com.devfox.bbvape.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUser(String username);
    Code signup(User user);
}
