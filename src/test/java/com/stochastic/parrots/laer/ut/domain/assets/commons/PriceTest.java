package com.stochastic.parrots.laer.ut.domain.assets.commons;

import com.stochastic.parrots.laer.domain.assets.commons.Issuer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.stochastic.parrots.laer.domain.assets.commons.Price;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PriceTest {

    @Test
    public void createPrice() {
        var price = Price.of(123.45);

        assertEquals("price", price.errors().context());
        assertTrue(price.errors().reasons().isEmpty());
        assertFalse(price.hasErrors());
        assertEquals(123.45, price.get());
    }

    @Test
    public void createPriceWithErrorWhenPassNegativeValue() {
        var price = Price.of(-1);

        assertEquals("price", price.errors().context());
        assertFalse(price.errors().reasons().isEmpty());
        assertEquals(-1, price.get());
        assertThat(price.errors().reasons())
                .hasSize(1)
                .containsOnly("price must be greater 0.0");
    }

    @Test
    public void hashCodes() {
        var price = Price.of(100.0).hashCode();
        var other = Price.of(10.0).hashCode();

        assertNotEquals(price, other);
    }

    @Test
    public void equals() {
        var price = Price.of(100.0);
        var other = Price.of(10.0);

        assertEquals(price, price);
        assertNotEquals(price, other);
    }
}
