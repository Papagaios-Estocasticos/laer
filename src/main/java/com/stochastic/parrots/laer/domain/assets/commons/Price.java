package com.stochastic.parrots.laer.domain.assets.commons;

import com.stochastic.parrots.laer.domain.shared.ValueObject;

import java.util.Objects;

public class Price extends ValueObject {
    private static final String CONTEXT = "price";
    private final double value;

    private Price(double value) {
        super(CONTEXT);
        this.value = value;
        validate();
    }

    private void validate() {
        if(value < 0.0) {
            errors.because("price must be greater 0.0");
        }
    }

    public static Price of(double value) {
        return new Price(value);
    }

    public double get() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                getClass().equals(other.getClass()) &&
                ((Price) other).value == value;
    }
}
