package com.banksystems.bankingsystemapp.services;

import com.banksystems.bankingsystemapp.exceptions.LoginIsAlreadyUsedException;
import com.banksystems.bankingsystemapp.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long userId);
    User addNewUser(User user) throws LoginIsAlreadyUsedException;
    void deleteUserById(Long userId);
}
