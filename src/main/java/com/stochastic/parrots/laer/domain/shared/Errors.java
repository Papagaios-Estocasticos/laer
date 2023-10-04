package com.stochastic.parrots.laer.domain.shared;

import java.util.LinkedList;
import java.util.List;

public class Errors {
    private final String context;
    private final List<String> reasons;

    private Errors(String context) {
        this.context = context;
        this.reasons = new LinkedList<>();
    }

    public static Errors on(String context) {
        return new Errors(context);
    }

    public void because(String reason) {
        this.reasons.add(reason);
    }

    public String context() {
        return this.context;
    }

    public List<String> reasons() {
        return this.reasons;
    }

    @Override
    public int hashCode() {
        return context.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                getClass().equals(other.getClass()) &&
                context.equals(((Errors) other).context);
    }
}
