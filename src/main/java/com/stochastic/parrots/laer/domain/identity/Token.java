package com.stochastic.parrots.laer.domain.identity;

import java.time.LocalDate;
import java.util.List;

public class Token {
    private final Long user;
    private final List<Role> roles;
    private final LocalDate createdAt;
    public Token(Long user, List<Role> roles, LocalDate createdAt) {
        this.user = user;
        this.roles = roles;
        this.createdAt = createdAt;
    }

    public Long user() {
        return this.user;
    }

    public List<Role> roles() {
        return this.roles;
    }

    public LocalDate createdAt() {
        return this.createdAt;
    }

    public boolean hasRole(Role role) {
        return roles.stream().anyMatch(r -> r.equals(role));
    }
}
