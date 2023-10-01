package com.stochastic.parrots.laer.ut.domain.assets.bonds;

import com.stochastic.parrots.laer.domain.assets.bonds.Information;
import com.stochastic.parrots.laer.domain.assets.bonds.PostFixed;
import com.stochastic.parrots.laer.domain.assets.bonds.PreFixed;
import com.stochastic.parrots.laer.domain.assets.commons.Issuer;
import com.stochastic.parrots.laer.domain.assets.indexers.IPCA;
import com.stochastic.parrots.laer.domain.assets.indexers.Indexer;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PostFixedTest {

    @Test
    public void createTreasury() {
        var treasury = PostFixed.treasury(123L, someInformation().maturity(MATURITY), INDEXER);

        assertEquals(123L, treasury.id());
        assertEquals(String.format("Tesouro %s %d", INDEXER, MATURITY.getYear()), treasury.name());
        assertEquals(INFORMATION.price, treasury.price());
        assertEquals(Issuer.government(), treasury.issuer());
        assertEquals(INFORMATION.maturity, treasury.maturity());
        assertEquals(INDEXER, treasury.indexer());
        assertEquals(0.225, treasury.incomeTaxRate());
        assertTrue(treasury.isActive());
    }

    @Test
    public void createCdb() {
        var cdb = PostFixed.cdb(123L,  someInformation().maturity(MATURITY), INDEXER);

        assertEquals(123L, cdb.id());
        assertEquals(String.format("CDB %s %d %s", INFORMATION.issuer, MATURITY.getYear(), INDEXER), cdb.name());
        assertEquals(INFORMATION.price, cdb.price());
        assertEquals(INFORMATION.issuer, cdb.issuer());
        assertEquals(INFORMATION.maturity, cdb.maturity());
        assertEquals(INDEXER, cdb.indexer());
        assertEquals(0.225, cdb.incomeTaxRate());
        assertTrue(cdb.isActive());
    }

    @Test
    public void incomeTaxRate() {
        var incomeTaxRates = List.of(
                PostFixed.lca(123L, someInformation(), INDEXER)
                        .incomeTaxRate(),
                PostFixed.cdb(123L, someInformation().maturity(NOW.plusDays(721)), INDEXER)
                        .incomeTaxRate(),
                PostFixed.cdb(123L, someInformation().maturity(NOW.plusDays(361)), INDEXER)
                        .incomeTaxRate(),
                PostFixed.cdb(123L, someInformation().maturity(NOW.plusDays(181)), INDEXER)
                        .incomeTaxRate(),
                PostFixed.cdb(123L, someInformation().maturity(NOW), INDEXER).incomeTaxRate());

        assertEquals(List.of(0.0, 0.15, 0.175, 0.20, 0.225), incomeTaxRates);
    }

    @Test
    public void createLca() {
        var lca = PostFixed.lca(123L,  someInformation().maturity(MATURITY), INDEXER);

        assertEquals(123L, lca.id());
        assertEquals(String.format("LCA %s %d %s", INFORMATION.issuer, MATURITY.getYear(), lca.indexer()), lca.name());
        assertEquals(INFORMATION.price, lca.price());
        assertEquals(INFORMATION.issuer, lca.issuer());
        assertEquals(INFORMATION.maturity, lca.maturity());
        assertEquals(INDEXER, lca.indexer());
        assertEquals(0.0, lca.incomeTaxRate());
        assertTrue(lca.isActive());
    }

    @Test
    public void createLci() {
        var lci = PostFixed.lci(123L,  someInformation().maturity(MATURITY), INDEXER);

        assertEquals(123L, lci.id());
        assertEquals(String.format("LCI %s %d %s", INFORMATION.issuer, MATURITY.getYear(), lci.indexer()), lci.name());
        assertEquals(INFORMATION.price, lci.price());
        assertEquals(INFORMATION.issuer, lci.issuer());
        assertEquals(INFORMATION.maturity, lci.maturity());
        assertEquals(INDEXER, lci.indexer());
        assertEquals(0.0, lci.incomeTaxRate());
        assertTrue(lci.isActive());
    }

    @Test
    public void createCra() {
        var cra = PostFixed.cra(123L, someInformation().maturity(MATURITY), INDEXER);

        assertEquals(123L, cra.id());
        assertEquals(String.format("CRA %s %d %s", INFORMATION.issuer, MATURITY.getYear(), cra.indexer()), cra.name());
        assertEquals(INFORMATION.price, cra.price());
        assertEquals(INFORMATION.issuer, cra.issuer());
        assertEquals(INFORMATION.maturity, cra.maturity());
        assertEquals(INDEXER, cra.indexer());
        assertEquals(0.0, cra.incomeTaxRate());
        assertTrue(cra.isActive());
    }

    @Test
    public void createCri() {
        var cri = PostFixed.cri(123L, someInformation().maturity(MATURITY), INDEXER);

        assertEquals(123L, cri.id());
        assertEquals(String.format("CRI %s %d %s", INFORMATION.issuer, MATURITY.getYear(), cri.indexer()), cri.name());
        assertEquals(INFORMATION.price, cri.price());
        assertEquals(INFORMATION.issuer, cri.issuer());
        assertEquals(INFORMATION.maturity, cri.maturity());
        assertEquals(INDEXER, cri.indexer());
        assertEquals(0.0, cri.incomeTaxRate());
        assertTrue(cri.isActive());
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
                        "interest rate must be greater 0.0"));
    }

    private static final LocalDate NOW = LocalDate.now();
    private static final LocalDate MATURITY = NOW.plusDays(10);
    private static final Indexer INDEXER = IPCA.plus(percentage(5.0));
    private static final Information INFORMATION = someInformation().maturity(MATURITY).build();
}
