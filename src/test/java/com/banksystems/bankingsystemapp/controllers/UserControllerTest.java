package com.banksystems.bankingsystemapp.controllers;

import com.banksystems.bankingsystemapp.models.User;
import com.banksystems.bankingsystemapp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void shouldGetUserById() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setLogin("login");
        user.setPassword("password");
        user.setEmail("email");
        userRepository.save(user);

    }

    @Test
    void addNewUser() {
    }

    @Test
    void deleteUserById() {
    }
}