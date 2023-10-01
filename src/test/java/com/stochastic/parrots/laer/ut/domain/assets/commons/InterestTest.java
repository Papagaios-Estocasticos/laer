package com.stochastic.parrots.laer.ut.domain.assets.commons;

import com.stochastic.parrots.laer.domain.assets.commons.Interest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InterestTest {

    @Test
    public void createInterestByPercentage() {
        var interest = Interest.percentage(10.0);

        assertEquals("interest", interest.errors().context());
        assertEquals(0.1, interest.rate());
        assertFalse(interest.hasErrors());
    }

    @Test
    public void createInterestByRate() {
        var interest = Interest.rate(0.1);

        assertEquals("interest", interest.errors().context());
        assertEquals(0.1, interest.rate());
        assertFalse(interest.hasErrors());
    }

    @Test
    public void createInterestWithErrorWhenPassNegativeValue() {
        var interest = Interest.percentage(-1);

        assertEquals("interest", interest.errors().context());
        assertTrue(interest.hasErrors());
        assertThat(interest.errors().reasons())
                .hasSize(1)
                .contains("interest rate must be greater 0.0");
    }

    @Test
    public void hashCodes() {
        var interest = Interest.percentage(100.0).hashCode();
        var sameInterest = Interest.rate(1.0).hashCode();
        var other = Interest.percentage(50.0).hashCode();

        assertEquals(interest, sameInterest);
        assertNotEquals(interest, other);
    }

    @Test
    public void equals() {
        var interest = Interest.percentage(100.0);
        var sameInterest = Interest.rate(1.0);
        var other = Interest.percentage(50.0);

        assertEquals(interest, sameInterest);
        assertNotEquals(interest, other);
    }
}
