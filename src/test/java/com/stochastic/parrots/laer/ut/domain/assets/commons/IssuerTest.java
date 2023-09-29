package com.stochastic.parrots.laer.ut.domain.assets.commons;

import com.stochastic.parrots.laer.domain.assets.commons.Issuer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IssuerTest {

    @Test
    public void createIssuer() {
        var issuer = Issuer.bank("bank boston");

        assertEquals("issuer", issuer.errors().context());
        assertTrue(issuer.errors().reasons().isEmpty());
        assertFalse(issuer.hasErrors());
        assertEquals("bank boston", issuer.get());
    }

    @Test
    public void createGovernmentIssuer() {
        var issuer = Issuer.government();

        assertEquals("issuer", issuer.errors().context());
        assertTrue(issuer.errors().reasons().isEmpty());
        assertFalse(issuer.hasErrors());
        assertEquals("Governo Federal", issuer.get());
    }

    @Test
    public void createIssuerWithErrorWhenPassEmptyName() {
        var issuer = Issuer.bank("");

        assertEquals("issuer", issuer.errors().context());
        assertFalse(issuer.errors().reasons().isEmpty());
        assertTrue(issuer.hasErrors());
        assertThat(issuer.errors().reasons())
                .hasSize(1)
                .containsOnly("issuer name cannot be empty");
    }

    @Test
    public void createIssuerWithErrorWhenPassBlankName() {
        var issuer = Issuer.bank("   ");

        assertEquals("issuer", issuer.errors().context());
        assertFalse(issuer.errors().reasons().isEmpty());
        assertTrue(issuer.hasErrors());
        assertThat(issuer.errors().reasons())
                .hasSize(1)
                .containsOnly("issuer name cannot be empty");
    }

    @Test
    public void createIssuerWithErrorWhenPassInvalidName() {
        var issuer = Issuer.bank("test");

        assertEquals("issuer", issuer.errors().context());
        assertFalse(issuer.errors().reasons().isEmpty());
        assertTrue(issuer.hasErrors());
        assertThat(issuer.errors().reasons())
                .hasSize(1)
                .containsOnly("issuer name must contain 5 to 10 letters");
    }

    @Test
    public void hashCodes() {
        var bank = Issuer.bank("bank boston").hashCode();
        var government = Issuer.government().hashCode();

        assertNotEquals(bank, government);
    }

    @Test
    public void equals() {
        var bank = Issuer.bank("bank boston");
        var government = Issuer.government();

        assertEquals(bank, bank);
        assertNotEquals(bank, government);
    }
}
