package com.stochastic.parrots.laer.domain.identity;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class Email {
    private static final Pattern pattern = compile("^\\S+@\\S+\\.\\S+$");
    private final String email;

    private static void valid(String email) {
        boolean matches = pattern.matcher(email).find();
        if (!matches) {
            throw new Invalid();
        }
    }

    private Email(String email) {
        valid(email);
        this.email = email;
    }

    public static Email of(String value) {
        return new Email(value);
    }

    public String get() {
        return this.email;
    }

    public static class Invalid extends RuntimeException { }
}
