package com.parpiiev.time.utils.validators;

import java.util.regex.Pattern;

public class PatternMatcher {

    private PatternMatcher() {
    }

    public static boolean validateName(String name) {
        if (name == null) return false;
        Pattern pattern = Pattern.compile("[a-zA-Z\\d]{1,15}");
        return pattern.matcher(name).matches();
    }

    public static boolean validateLogin(String login) {
        if (login == null) return false;
        Pattern pattern = Pattern.compile("[A-Za-z._]{1,15}");
        return pattern.matcher(login).matches();
    }

    public static boolean validatePassword(String password) {
        if (password == null) return false;
        Pattern pattern = Pattern.compile("[A-Za-z._@!#$%^&*()\\d]{2,15}");
        return pattern.matcher(password).matches();
    }

    public static boolean validateEmail(String email) {
        if (email == null) return false;
        Pattern pattern = Pattern.compile("[A-Za-z\\d._]{2,25}@[A-Za-z\\d.]{2,15}\\.[A-Za-z]{2,6}");
        return pattern.matcher(email).matches();
    }
}
