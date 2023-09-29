package com.stochastic.parrots.laer.domain.assets.commons;

import com.stochastic.parrots.laer.domain.shared.ValueObject;

import java.util.Objects;

public class Interest extends ValueObject {
    private static final String CONTEXT = "interest";
    private final double rate;

    private Interest(double value) {
        super(CONTEXT);
        this.rate = value;
        validate();
    }

    private void validate() {
        if(rate < 0.0 || rate > 1.0) {
            errors.because("interest rate must be between 0.0 and 1.0");
        }
    }

    public double rate() {
        return rate;
    }

    public static Interest percentage(double percentage) {
        return new Interest(percentage / 100);
    }

    public static Interest rate(double rate) {
        return new Interest(rate);
    }

    @Override
    public String toString() {
        return String.format("%.2f", rate * 100) + "%";
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                getClass().equals(other.getClass()) &&
                ((Interest) other).rate == rate;
    }
}
