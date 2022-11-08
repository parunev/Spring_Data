package com.example.demo.annotations;

import com.example.demo.models.User;
import org.passay.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class PassConstraintValidator {
    public static void isValid(String value) {

        Field passwordField = Arrays.stream(User.class.getDeclaredFields()).filter(f -> f.getAnnotation(Password.class) != null)
                .findFirst().orElse(null);

        if (passwordField == null || !passwordField.getName().equals("password")) {
            throw new IllegalArgumentException("No password found!");
        }

        Password passAnnotation = passwordField.getAnnotation(Password.class);

        int maxLength = passAnnotation.maxLength();
        int minLength = passAnnotation.minLength();
        boolean containsDigit = passAnnotation.containsDigit();
        boolean containsLowercase = passAnnotation.containsLowercase();
        boolean containsUppercase = passAnnotation.containsUppercase();
        boolean containsSpecialSymbol = passAnnotation.containsSpecialSymbol();

        ArrayList<Rule> rules = createRules(maxLength, minLength, containsDigit, containsLowercase, containsUppercase, containsSpecialSymbol);

        PasswordValidator validator = new PasswordValidator(rules);

        RuleResult result = validator.validate(new PasswordData(value));

        if (!result.isValid()) {
            throw new IllegalArgumentException(passAnnotation.message());
        }
    }

    private static ArrayList<Rule> createRules(int maxLength, int minLength, boolean containsDigit, boolean containsLowercase, boolean containsUppercase, boolean containsSpecialSymbol) {
        ArrayList<Rule> rules = new ArrayList<>();
        rules.add(new LengthRule(minLength, maxLength));

        if (containsDigit) {
            rules.add(new DigitCharacterRule(1));
        }

        if (containsLowercase) {
            rules.add(new LowercaseCharacterRule(1));
        }

        if (containsUppercase) {
            rules.add(new UppercaseCharacterRule(1));
        }

        if (containsSpecialSymbol) {
            rules.add(new SpecialCharacterRule(1));
        }
        return rules;
    }
}