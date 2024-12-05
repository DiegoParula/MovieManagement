package com.moviemanagment.moviemanagment.service.Validator;

import com.moviemanagment.moviemanagment.exception.InvalidPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

//validando creando un componente sin usar anotaciones
public class PasswordValidator {
    public static void validatePassword(String password, String confirmPassword) {
        if (!StringUtils.hasText(password) || !StringUtils.hasText(confirmPassword)) {
            throw new IllegalArgumentException("\"Password and confirm password are required\"");
        }

        if (!password.equals(confirmPassword)) {
            throw new InvalidPasswordException(password, confirmPassword,  "Passwords do not match");
        }

        if (!containsNumber(password)){
            throw new InvalidPasswordException(password, "Passwords do not contain numbers");
        }
        if (!containsUpperCase(password)){
            throw new InvalidPasswordException(password, "Passwords must contains al least one uppercase letter");
        }


        if (!containsLowerCase(password)){
            throw new InvalidPasswordException(password, "Passwords must contains al least one lowercase letter");

        }


        if (!containsSpecialCharacter(password)){
            throw new InvalidPasswordException(password, "Passwords must contains al least one special character");
        }
    }

    private static boolean containsSpecialCharacter(String password) {
        return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?].*");
    }

    private static boolean containsLowerCase(String password) {
        return password.matches(".*[a-z].*");
    }

    private static boolean containsUpperCase(String password) {
        return password.matches(".*[A-Z].*");
    }

    private static boolean containsNumber(String password) {
        return password.matches(".*\\d.*");
       }
}
