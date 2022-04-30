package com.banksystems.bankingsystemapp.services.impl;

import com.banksystems.bankingsystemapp.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static com.sun.javaws.JnlpxArgs.verify;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepositoryTest;
    private AutoCloseable autoCloseable;
    private UserServiceImpl userServiceTest;

    @BeforeEach
    void setUp() {
        AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
        userServiceTest = new UserServiceImpl(userRepositoryTest);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getUserById() {
        //when
        userServiceTest.getUserById(1L);
        //then
    }

    @Test
    @Disabled
    void addNewUser() {
    }

    @Test
    @Disabled
    void deleteUserById() {
    }
}