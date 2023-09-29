package com.stochastic.parrots.laer.domain.shared;

import java.util.stream.Stream;

public class ValueObject {
    protected final Errors errors;

    public ValueObject(String context) {
        this.errors = Errors.on(context);
    }

    public Errors errors() {
        return errors;
    }

    protected final void validate(ValueObject... valueObjects) {
        Stream.of(valueObjects)
                .filter(ValueObject::hasErrors)
                .map(ValueObject::errors)
                .map(Errors::reasons)
                .forEach(reasons -> reasons.forEach(errors::because));
    }

    public boolean hasErrors() {
        return !errors().reasons().isEmpty();
    }
}
