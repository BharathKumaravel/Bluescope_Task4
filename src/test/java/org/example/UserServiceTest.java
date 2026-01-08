package org.example;

import org.example.config.Hashing;
import org.example.config.JwtGeneration;

import org.example.dao.UserDAO;

import org.example.model.User;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
     UserService userService ;
     UserDAO userDAO;
     JwtGeneration jwtGeneration;
    Hashing hashing;
    @BeforeEach
    void setup() throws Exception {

        userService= new UserService();
        userDAO= mock(UserDAO.class);
        jwtGeneration=mock(JwtGeneration.class);



        var field = UserService.class.getDeclaredField("userDAO");
        field.setAccessible(true);
        field.set(userService, userDAO);


    }

    @Test
    void testAddUser() {

        User user = new User();

        userService.addUser(user);

        verify(userDAO).addUser(user);
    }
    @Test
    void testVerify(){

        String mail = "abc@gmail.com";
        String password = "1234";
        String realHash = "$2a$10$7s9Wq1Nn8OZpWwzKpK9P5eFj8Z3q9Z9yYpQm0YJ2yXnO8Fv7u";

        when(userDAO.getPass(mail)).thenReturn(realHash);

        String token = userService.verify(mail, password);

        assertNotNull(token,"Expected");
        assertFalse(token.isBlank(),"Expected");
    }
    }

