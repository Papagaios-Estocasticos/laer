package com.stochastic.parrots.laer.domain.shared;

import java.util.Objects;
import java.util.stream.Stream;

public abstract class Entity {
    protected final Long id;
    protected final Errors errors;

    protected Entity(Long id, String context) {
        this.id = id;
        this.errors = Errors.on(context);
    }

    protected final void validate(ValueObject... valueObjects) {
        Stream.of(valueObjects).filter(ValueObject::hasErrors)
                .map(ValueObject::errors)
                .map(Errors::reasons)
                .forEach(reasons -> reasons.forEach(errors::because));
        if(!errors.reasons().isEmpty()) {
            throw new InvalidEntity(errors);
        }
    }

    public Long id() {
        return this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                getClass().equals(other.getClass()) &&
                id.equals(((Entity) other).id);
    }

    public static class InvalidEntity extends RuntimeException {
        private final Errors errors;
        public InvalidEntity(Errors errors) {
            this.errors = errors;
        }
        public Errors errors() {
            return this.errors;
        }
    }
}
