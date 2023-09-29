package com.stochastic.parrots.laer.domain.assets.commons;

import com.stochastic.parrots.laer.domain.shared.ValueObject;

public class Issuer extends ValueObject {
    private static final String CONTEXT = "issuer";
    private final String name;

    private Issuer(String name) {
        super(CONTEXT);
        this.name = name;
        validate();
    }

    private void validate() {
        if(name.isBlank() || name.isEmpty()) {
            errors.because("issuer name cannot be empty");
            return;
        }
        if(name.length() <= 4 || name.length() > 15) {
            errors.because("issuer name must contain 5 to 10 letters");
        }
    }

    public static Issuer bank(String name) {
        return new Issuer(name);
    }

    public static Issuer government() {
        return new Issuer("Governo Federal");
    }

    public String get() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                getClass().equals(other.getClass()) &&
                ((Issuer) other).name.equals(name);
    }
}
