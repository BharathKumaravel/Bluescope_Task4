package org.example.service;

import org.example.config.Hashing;
import org.example.config.JwtGeneration;
import org.example.dao.UserDAO;
import org.example.model.User;

public class UserService {



    UserDAO userDAO =new UserDAO();

    public void addUser(User user) {

        String pass = user.getPassword();
        user.setPassword(Hashing.hashPassword(pass));

        userDAO.addUser(user);

    }


    public String verify(String mail , String password){
        String hashPassword = userDAO.getPass(mail);
        if(hashPassword !=null) {
            boolean isValid = Hashing.verifyPassword(password, hashPassword);
            if (isValid) {
                return JwtGeneration.generateToken(mail);
            } else {
                return "Password wrong";
            }
        }
        else {
            return "No mail Found in this mailId please register";
        }
    }
}
