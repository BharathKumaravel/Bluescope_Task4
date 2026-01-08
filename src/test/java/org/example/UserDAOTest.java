package org.example;


import org.example.dao.UserDAO;

import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;


 class UserDAOTest {

    private UserDAO userDAO;

    @BeforeEach
    void set() throws SQLException {
         userDAO = new UserDAO();

       Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankBranchManagement","root","root");
        Statement stmt =con.createStatement();
        stmt.execute("DELETE FROM users WHERE email = 'aaa@gmail.com' ");

    }
    @Test
    void testAddUser() {

        User user  = createUser();

        userDAO.addUser(user);

        assertNotNull(user.getPassword(),"Expected");
        assertEquals("1234", userDAO.getPass(user.getEmail()),"Expected"); // password must be hashed



    }
    public User createUser(){
        User user = new User();
        user.setEmail("aaa@gmail.com");
        user.setPassword("1234");
        return user;
    }
}


