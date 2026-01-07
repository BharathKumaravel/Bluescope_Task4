package org.example.service;

import org.example.config.Hashing;
import org.example.config.JwtGeneration;
import org.example.dao.UserDAO;
import org.example.model.User;

public class UserService {

    Hashing hashing = new Hashing();
    UserDAO userDAO =new UserDAO();
    JwtGeneration jwtGeneration = new JwtGeneration();

    public void addUser(User user) {

        String pass = user.getPassword();
        user.setPassword(hashing.hashPassword(pass));

        userDAO.addUser(user);

    }

    public String verify(String mail , String password){
        String hashPassword = userDAO.getPass(mail);
        if(hashPassword !=null) {
            boolean isValid = hashing.verifyPassword(password, hashPassword);
            if (isValid) {
                return jwtGeneration.generateToken(mail);
            } else {
                return "Password wrong";
            }
        }
        else {
            return "No mail Found in this mailId please register";
        }
    }
}
