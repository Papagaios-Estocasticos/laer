package com.stochastic.parrots.laer.domain.identity;

import java.util.List;

public class User {
    private final Long id;
    private final Email email;
    private final Name name;
    private final Password password;
    private final List<Role> roles;
    private final boolean active;

    private static void valid(List<Role> roles) {
        if(roles.isEmpty()){
            throw new Invalid();
        }
    }

    private User(Builder builder) {
        valid(builder.roles);
        this.email = builder.email;
        this.id = builder.id;
        this.name = builder.name;
        this.password = builder.password;
        this.roles = builder.roles;
        this.active = builder.active;
    }

    public Long id() {
        return this.id;
    }

    public Email email() {
        return this.email;
    }

    public Name name() {
        return this.name;
    }

    public Password password() {
        return this.password;
    }

    public List<Role> roles() {
        return this.roles;
    }

    public boolean isActive() {
        return this.active;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other != null &&
                getClass().equals(other.getClass()) &&
                ((User) other).id.equals(this.id);
    }

    public static class Builder {
        private Long id;
        private Email email;
        private Name name;
        private Password password;
        private List<Role> roles;
        private boolean active;

        public Builder with(Long id) {
            this.id = id;
            return this;
        }

        public Builder with(Name name) {
            this.name = name;
            return this;
        }

        public Builder with(Email email) {
            this.email = email;
            return this;
        }

        public Builder with(Password password) {
            this.password = password;
            return this;
        }

        public Builder with(List<Role> roles) {
            this.roles = roles;
            return this;
        }

        public Builder activated() {
            this.active = true;
            return this;
        }

        public Builder deactivated() {
            this.active = false;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public static class Invalid extends RuntimeException {}
}
