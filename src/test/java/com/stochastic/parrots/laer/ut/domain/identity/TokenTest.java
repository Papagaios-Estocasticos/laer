package com.stochastic.parrots.laer.ut.domain.identity;

import com.stochastic.parrots.laer.domain.identity.Role;
import com.stochastic.parrots.laer.domain.identity.Token;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TokenTest {

    @Test
    public void createToken() {
        var now = LocalDate.now();
        var token = new Token(123L, List.of(Role.USER), now);

        assertEquals(123L, token.user());
        assertEquals(List.of(Role.USER), token.roles());
        assertEquals(now, token.createdAt());
    }

    @Test
    public void haveRole() {
        var token = new Token(123L, List.of(Role.USER), LocalDate.now());
        assertTrue(token.hasRole(Role.USER));
    }

    @Test
    public void haveNotRole() {
        var token = new Token(123L, List.of(Role.USER), LocalDate.now());
        assertFalse(token.hasRole(Role.ADMIN));
    }
}
