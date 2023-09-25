package com.stochastic.parrots.laer.domain.identity;

import java.util.Optional;

public interface UserRepository {
    Optional<User> create(User user);
    Optional<User> findUserById(Long id);
    Optional<User> findUserBy(Email email);
    void update(User user);
}
