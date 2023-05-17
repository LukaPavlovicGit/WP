package com.example.homework6.repository;

import com.example.homework6.entities.User;

public interface UserRepository {
    public User findUser(String username);
    public Boolean insert(User user);
}
