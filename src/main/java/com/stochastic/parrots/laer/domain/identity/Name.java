package com.stochastic.parrots.laer.domain.identity;

public class Name {
    private static final int minimumLength = 3;
    private static final int maximumLength = 15;
    private final String name;

    private static void valid(String name) {
        boolean isBlankOrEmpty = name.isEmpty() || name.isBlank();
        boolean inValidRange = minimumLength <= name.length() && name.length() <= maximumLength;
        if (isBlankOrEmpty || !inValidRange) {
            throw new Invalid();
        }
    }

    private Name(String name) {
        valid(name);
        this.name = name;
    }

    public static Name of(String value) {
        return new Name(value);
    }

    public String get() {
        return this.name;
    }

    public static class Invalid extends RuntimeException { }
}
