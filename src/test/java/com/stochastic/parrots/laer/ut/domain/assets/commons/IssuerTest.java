package com.stochastic.parrots.laer.ut.domain.assets.commons;

import com.stochastic.parrots.laer.domain.assets.commons.Issuer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IssuerTest {

    @Test
    public void createIssuer() {
        var issuer = Issuer.of("bank boston");

        assertEquals("issuer", issuer.errors().context());
        assertTrue(issuer.errors().reasons().isEmpty());
        assertFalse(issuer.hasErrors());
        assertEquals("bank boston", issuer.get());
    }


    @Test
    public void createIssuerWithErrorWhenPassEmptyName() {
        var issuer = Issuer.of("");

        assertEquals("issuer", issuer.errors().context());
        assertFalse(issuer.errors().reasons().isEmpty());
        assertTrue(issuer.hasErrors());
        assertThat(issuer.errors().reasons())
                .hasSize(1)
                .containsOnly("issuer name cannot be empty");
    }

    @Test
    public void createIssuerWithErrorWhenPassBlankName() {
        var issuer = Issuer.of("   ");

        assertEquals("issuer", issuer.errors().context());
        assertFalse(issuer.errors().reasons().isEmpty());
        assertTrue(issuer.hasErrors());
        assertThat(issuer.errors().reasons())
                .hasSize(1)
                .containsOnly("issuer name cannot be empty");
    }

    @Test
    public void createIssuerWithErrorWhenPassInvalidName() {
        var issuer = Issuer.of("test");

        assertEquals("issuer", issuer.errors().context());
        assertFalse(issuer.errors().reasons().isEmpty());
        assertTrue(issuer.hasErrors());
        assertThat(issuer.errors().reasons())
                .hasSize(1)
                .containsOnly("issuer name must contain 5 to 10 letters");
    }

    @Test
    public void hashCodes() {
        var bank = Issuer.of("bank boston").hashCode();
        var other = Issuer.of("bank u.s.a").hashCode();

        assertNotEquals(bank, other);
    }

    @Test
    public void equals() {
        var bank = Issuer.of("bank boston");
        var other = Issuer.of("bank u.s.a");

        assertEquals(bank, bank);
        assertNotEquals(bank, other);
    }
}
