package org.example.config;

import java.util.Base64;

public class EncryptDecrypt {

    public static String encrypt(String role){
        return Base64.getEncoder().encodeToString(role.getBytes());
    }

    public static String decode(String encodedText) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedText);
        return new String(decodedBytes);
    }
}
