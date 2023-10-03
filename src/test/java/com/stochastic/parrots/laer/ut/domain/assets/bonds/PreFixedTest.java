package com.stochastic.parrots.laer.ut.domain.assets.bonds;

import com.stochastic.parrots.laer.domain.assets.bonds.Bond;
import com.stochastic.parrots.laer.domain.assets.bonds.PreFixed;
import com.stochastic.parrots.laer.domain.shared.Entity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.stochastic.parrots.laer.domain.assets.commons.Interest.percentage;
import static com.stochastic.parrots.laer.fixture.domain.assets.commons.InformationFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PreFixedTest {

    @Test
    public void createWithIncomeTax() {
        var information = someCDBInformationWith(MATURITY);
        var cdb = PreFixed.withIncomeTax(123L, information, percentage(13.0));

        assertEquals(123L, cdb.id());
        assertEquals(String.format("CDB %d %s", information.maturity.getYear(), cdb.interest()), cdb.name());
        assertEquals(information.price, cdb.price());
        assertEquals(information.issuer, cdb.issuer());
        assertEquals(information.maturity, cdb.maturity());
        assertEquals(0.13, cdb.interest().rate());
        assertEquals(0.225, cdb.incomeTaxRate());
        assertTrue(cdb.isActive());
        assertFalse(cdb.haveIncomeTaxException());
    }

    @Test
    public void createWithoutIncomeTax() {
        var information = someLCAInformationWith(MATURITY);
        var lca = PreFixed.withoutIncomeTax(123L, information, percentage(8.0));

        assertEquals(123L, lca.id());
        assertEquals(String.format("LCA %d %s", information.maturity.getYear(), lca.interest()), lca.name());
        assertEquals(information.price, lca.price());
        assertEquals(information.issuer, lca.issuer());
        assertEquals(information.maturity, lca.maturity());
        assertEquals(0.08, lca.interest().rate());
        assertEquals(0.0, lca.incomeTaxRate());
        assertTrue(lca.isActive());
        assertTrue(lca.haveIncomeTaxException());
    }

    @Test
    public void incomeTaxRate() {
        var incomeTaxRates = Stream.of(
                PreFixed.withoutIncomeTax(1L, someLCAInformationWith(NOW), percentage(5)),
                PreFixed.withIncomeTax(2L, someCDBInformationWith(NOW.plusDays(721)), percentage(5)),
                PreFixed.withIncomeTax(3L, someCDBInformationWith(NOW.plusDays(361)), percentage(5)),
                PreFixed.withIncomeTax(4L, someCDBInformationWith(NOW.plusDays(181)), percentage(5)),
                PreFixed.withIncomeTax(5L, someCDBInformationWith(NOW), percentage(5.0)))
                .map(Bond::incomeTaxRate).collect(Collectors.toList());

        assertEquals(List.of(0.0, 0.15, 0.175, 0.20, 0.225), incomeTaxRates);
    }

    @Test
    public void throwsInvalidEntityExceptionWhenPassInvalidValueObjects() {
        var exception = assertThrows(Entity.InvalidEntity.class, () ->
                PreFixed.withoutIncomeTax(123L, someInformationWithAllFieldsInvalid(), percentage(-5)));

        assertEquals("pre-fixed bond", exception.errors().context());
        assertThat(exception.errors().reasons())
                .hasSize(5)
                .containsAll(List.of(
                        "bond name must be in ['LCA', 'LCI', 'CRA', 'CRI']",
                        "bond name must contain 3 to 17 letters",
                        "price must be greater 0.0",
                        "issuer name must contain 5 to 10 letters",
                        "interest rate must be greater 0.0"));
    }

    private static final LocalDate NOW = LocalDate.now();
    private static final LocalDate MATURITY = NOW.plusDays(10);
}
