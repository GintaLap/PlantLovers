package com.example.plant_lovers.security;


import org.springframework.security.crypto.bcrypt.BCrypt;

public class Password {
    private static int workload = 12;

    public static String hashPassword(String password) {

        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(password, salt);

        return hashed_password;

    }

    public static boolean checkPassword(String password, String storedHash) {
        boolean password_verified = false;

        if(null == storedHash || !storedHash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

        password_verified = BCrypt.checkpw(password, storedHash);

        return password_verified;
    }

}
