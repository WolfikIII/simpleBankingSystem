package com.banksystems.bankingsystemapp.controllers;

import com.banksystems.bankingsystemapp.exceptions.LoginIsAlreadyUsedException;
import com.banksystems.bankingsystemapp.models.User;
import com.banksystems.bankingsystemapp.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService){
        this.userService = userService;
    }

    @GetMapping("/index")
    public Optional<User> getUserById(@RequestParam Long userId){
        return userService.getUserById(userId);
    }

    @PostMapping("/")
    public ResponseEntity<User> addNewUser(@RequestBody User user) throws LoginIsAlreadyUsedException {
        return new ResponseEntity<>(userService.addNewUser(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/")
    public void deleteUserById(@RequestParam Long userId){
        userService.deleteUserById(userId);
    }
}
