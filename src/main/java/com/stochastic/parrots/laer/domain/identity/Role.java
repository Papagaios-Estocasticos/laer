package com.stochastic.parrots.laer.domain.identity;

import java.util.Arrays;
import java.util.Optional;

public enum Role {
    ADMIN, USER;

    public static Optional<Role> exists(String role) {
        var normalized = role.toUpperCase();
        return Arrays.stream(Role.values())
                .filter(r -> r.name().equals(normalized))
                .findFirst();
    }
}
