package com.banksystems.bankingsystemapp.services.impl;

import com.banksystems.bankingsystemapp.exceptions.LoginIsAlreadyUsedException;
import com.banksystems.bankingsystemapp.models.User;
import com.banksystems.bankingsystemapp.repositories.UserRepository;
import com.banksystems.bankingsystemapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserById(Long userId){
        return userRepository.findById(userId);
    }

    @Override
    public User addNewUser(User user) throws LoginIsAlreadyUsedException {
        if(userRepository.findUserByLogin(user.getLogin()) != null)
            throw new LoginIsAlreadyUsedException("Login is already used!");
        else
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void basicDataUsers(){
        userRepository.save(new User(1L, "Mysza1", "haslo1", "email@wp.pl"));
        userRepository.save(new User(2L, "Mysza2", "haslo2", "email1@wp.pl"));
        userRepository.save(new User(3L, "Mysza3", "haslo3", "email2@wp.pl"));
        userRepository.save(new User(4L, "Mysza4", "haslo4", "email3@wp.pl"));
    }

}
