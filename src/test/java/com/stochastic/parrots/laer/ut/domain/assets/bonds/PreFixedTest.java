package com.stochastic.parrots.laer.ut.domain.assets.bonds;

import com.stochastic.parrots.laer.domain.assets.bonds.Information;
import com.stochastic.parrots.laer.domain.assets.bonds.PreFixed;
import com.stochastic.parrots.laer.domain.assets.commons.Issuer;
import com.stochastic.parrots.laer.domain.shared.Entity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static com.stochastic.parrots.laer.domain.assets.commons.Interest.percentage;
import static com.stochastic.parrots.laer.fixture.domain.assets.commons.InformationFixture.someInformation;
import static com.stochastic.parrots.laer.fixture.domain.assets.commons.InformationFixture.someInvalidInformation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PreFixedTest {

    @Test
    public void createTreasury() {
        var treasury = PreFixed.treasury(123L, someInformation().maturity(MATURITY), percentage(13.0));

        assertEquals(123L, treasury.id());
        assertEquals(String.format("Tesouro Préfixado %d %s", MATURITY.getYear(), treasury.interest()), treasury.name());
        assertEquals(INFORMATION.price, treasury.price());
        assertEquals(Issuer.government(), treasury.issuer());
        assertEquals(INFORMATION.maturity, treasury.maturity());
        assertEquals(0.13, treasury.interest().rate());
        assertEquals(0.225, treasury.incomeTaxRate());
        assertTrue(treasury.isActive());
    }

    @Test
    public void createCdb() {
        var cdb = PreFixed.cdb(123L,  someInformation().maturity(MATURITY), percentage(10.0));

        assertEquals(123L, cdb.id());
        assertEquals(String.format("CDB %s %d %s", INFORMATION.issuer, MATURITY.getYear(), cdb.interest()), cdb.name());
        assertEquals(INFORMATION.price, cdb.price());
        assertEquals(INFORMATION.issuer, cdb.issuer());
        assertEquals(INFORMATION.maturity, cdb.maturity());
        assertEquals(0.1, cdb.interest().rate());
        assertEquals(0.225, cdb.incomeTaxRate());
        assertTrue(cdb.isActive());
    }

    @Test
    public void incomeTaxRate() {
        var incomeTaxRates = List.of(
                PreFixed.lca(123L, someInformation(), percentage(5))
                        .incomeTaxRate(),
                PreFixed.cdb(123L, someInformation().maturity(NOW.plusDays(721)), percentage(10.0))
                        .incomeTaxRate(),
                PreFixed.cdb(123L, someInformation().maturity(NOW.plusDays(361)), percentage(5.0))
                        .incomeTaxRate(),
                PreFixed.cdb(123L, someInformation().maturity(NOW.plusDays(181)), percentage(5.0))
                        .incomeTaxRate(),
                PreFixed.cdb(123L, someInformation().maturity(NOW), percentage(5.0)).incomeTaxRate());

        assertEquals(List.of(0.0, 0.15, 0.175, 0.20, 0.225), incomeTaxRates);
    }

    @Test
    public void createLca() {
        var lca = PreFixed.lca(123L,  someInformation().maturity(MATURITY), percentage(8.0));

        assertEquals(123L, lca.id());
        assertEquals(String.format("LCA %s %d %s", INFORMATION.issuer, MATURITY.getYear(), lca.interest()), lca.name());
        assertEquals(INFORMATION.price, lca.price());
        assertEquals(INFORMATION.issuer, lca.issuer());
        assertEquals(INFORMATION.maturity, lca.maturity());
        assertEquals(0.08, lca.interest().rate());
        assertEquals(0.0, lca.incomeTaxRate());
        assertTrue(lca.isActive());
    }

    @Test
    public void createLci() {
        var lca = PreFixed.lci(123L,  someInformation().maturity(MATURITY), percentage(8.0));

        assertEquals(123L, lca.id());
        assertEquals(String.format("LCI %s %d %s", INFORMATION.issuer, MATURITY.getYear(), lca.interest()), lca.name());
        assertEquals(INFORMATION.price, lca.price());
        assertEquals(INFORMATION.issuer, lca.issuer());
        assertEquals(INFORMATION.maturity, lca.maturity());
        assertEquals(0.08, lca.interest().rate());
        assertEquals(0.0, lca.incomeTaxRate());
        assertTrue(lca.isActive());
    }

    @Test
    public void createCra() {
        var lca = PreFixed.cra(123L, someInformation().maturity(MATURITY), percentage(8.0));

        assertEquals(123L, lca.id());
        assertEquals(String.format("CRA %s %d %s", INFORMATION.issuer, MATURITY.getYear(), lca.interest()), lca.name());
        assertEquals(INFORMATION.price, lca.price());
        assertEquals(INFORMATION.issuer, lca.issuer());
        assertEquals(INFORMATION.maturity, lca.maturity());
        assertEquals(0.08, lca.interest().rate());
        assertEquals(0.0, lca.incomeTaxRate());
        assertTrue(lca.isActive());
    }

    @Test
    public void createCri() {
        var lca = PreFixed.cri(123L, someInformation().maturity(MATURITY), percentage(8.0));

        assertEquals(123L, lca.id());
        assertEquals(String.format("CRI %s %d %s", INFORMATION.issuer, MATURITY.getYear(), lca.interest()), lca.name());
        assertEquals(INFORMATION.price, lca.price());
        assertEquals(INFORMATION.issuer, lca.issuer());
        assertEquals(INFORMATION.maturity, lca.maturity());
        assertEquals(0.08, lca.interest().rate());
        assertEquals(0.0, lca.incomeTaxRate());
        assertTrue(lca.isActive());
    }

    @Test
    public void throwsInvalidEntityExceptionWhenPassInvalidInformation() {
        var exception = assertThrows(Entity.InvalidEntity.class, () ->
                PreFixed.treasury(123L, someInformation().price(-1.0), percentage(5)));

        assertEquals("pre-fixed bond", exception.errors().context());
        assertThat(exception.errors().reasons())
                .hasSize(1)
                .containsAll(List.of("price must be greater 0.0"));
    }

    @Test
    public void throwsInvalidEntityExceptionWhenPassInvalidValueObjects() {
        var exception = assertThrows(Entity.InvalidEntity.class, () ->
                PreFixed.cdb(123L, someInvalidInformation(), percentage(-5)));

        assertEquals("pre-fixed bond", exception.errors().context());
        assertThat(exception.errors().reasons())
                .hasSize(3)
                .containsAll(List.of(
                        "price must be greater 0.0",
                        "issuer name must contain 5 to 10 letters",
                        "interest rate must be between 0.0 and 1.0"));
    }

    private static final LocalDate NOW = LocalDate.now();
    private static final LocalDate MATURITY = NOW.plusDays(10);
    private static final Information INFORMATION = someInformation().maturity(MATURITY).build();
}
