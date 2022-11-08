package com.example.demo.annotations;

import com.example.demo.models.User;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailConstraintValidator {

    public static void isValid(String value) {
        Field emailField = Arrays.stream(User.class.getDeclaredFields()).filter(f -> f.getAnnotation(Email.class) != null)
                .findFirst().orElse(null);

        if (emailField == null || !emailField.getName().equals("email")) {
            throw new IllegalArgumentException("No email!");
        }

        Pattern pattern = Pattern.compile("^(?<user>[a-zA-Z0-9]+[.\\-_]*[a-zA-Z]+)@(?<host>[A-Za-z]+.*\\.[A-Za-z]+)$");
        Matcher matcher = pattern.matcher(value);

        if (!matcher.find()) throw new IllegalArgumentException(emailField.getAnnotation(Email.class).message());
    }
}
