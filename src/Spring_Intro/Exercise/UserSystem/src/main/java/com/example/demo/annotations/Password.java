package com.example.demo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Password {
    String message() default "Invalid password";
    int minLength() default 1;
    int maxLength() default 100;
    boolean containsDigit() default false;
    boolean containsLowercase() default false;
    boolean containsUppercase() default false;
    boolean containsSpecialSymbol() default false;
}
