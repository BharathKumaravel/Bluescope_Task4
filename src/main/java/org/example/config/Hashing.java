package org.example.config;
import org.mindrot.jbcrypt.BCrypt;

public class Hashing {
    public String hashPassword(String pass){
        return BCrypt.hashpw(pass,BCrypt.gensalt(5));
    }

    public boolean verifyPassword(String password, String hashPassword) {
        return BCrypt.checkpw(password,hashPassword);
    }
}
