package com.stochastic.parrots.laer.domain.identity;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class Password {
    private static final Pattern pattern = compile("(([A-z].*[0-9].*)|([0-9].*[A-z].*))");
    private static final int minimumLength = 10;
    private final String password;

    private static void valid(String password) {
        boolean isBlankOrEmpty = password.isEmpty() || password.isBlank();
        boolean inValidRange = minimumLength <= password.length();
        boolean matches = pattern.matcher(password).find();
        if (isBlankOrEmpty || !inValidRange || !matches) {
            throw new Invalid();
        }
    }

    private Password(String password) {
        valid(password);
        this.password = password;
    }

    public static Password of(String value) {
        return new Password(value);
    }

    public String get() {
        return this.password;
    }

    public interface Encoder {
        String encode(Password password);
    }

    public static class Invalid extends RuntimeException { }
}
