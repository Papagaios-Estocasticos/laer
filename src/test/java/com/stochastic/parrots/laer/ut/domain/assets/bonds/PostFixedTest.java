package com.stochastic.parrots.laer.ut.domain.assets.bonds;

import com.stochastic.parrots.laer.domain.assets.bonds.Bond;
import com.stochastic.parrots.laer.domain.assets.bonds.PostFixed;
import com.stochastic.parrots.laer.domain.assets.indexers.IPCA;
import com.stochastic.parrots.laer.domain.assets.indexers.Indexer;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PostFixedTest {

    @Test
    public void createWithIncomeTax() {
        var information = someCDBInformationWith(MATURITY);
        var cdb = PostFixed.withIncomeTax(123L, information, INDEXER);

        assertEquals(123L, cdb.id());
        assertEquals(String.format("CDB %d %s", information.maturity.getYear(), cdb.indexer()), cdb.name());
        assertEquals(information.price, cdb.price());
        assertEquals(information.issuer, cdb.issuer());
        assertEquals(information.maturity, cdb.maturity());
        assertEquals(INDEXER, cdb.indexer());
        assertEquals(0.225, cdb.incomeTaxRate());
        assertTrue(cdb.isActive());
    }

    @Test
    public void createWithoutIncomeTax() {
        var information = someLCAInformationWith(MATURITY);
        var lca = PostFixed.withoutIncomeTax(123L, information, INDEXER);

        assertEquals(123L, lca.id());
        assertEquals(String.format("LCA %d %s", information.maturity.getYear(), lca.indexer()), lca.name());
        assertEquals(information.price, lca.price());
        assertEquals(information.issuer, lca.issuer());
        assertEquals(information.maturity, lca.maturity());
        assertEquals(INDEXER, lca.indexer());
        assertEquals(0.0, lca.incomeTaxRate());
        assertTrue(lca.isActive());
    }

    @Test
    public void incomeTaxRate() {
        var incomeTaxRates = Stream.of(
                PostFixed.withoutIncomeTax(1L, someLCAInformationWith(NOW), INDEXER),
                PostFixed.withIncomeTax(2L, someCDBInformationWith(NOW.plusDays(721)), INDEXER),
                PostFixed.withIncomeTax(3L, someCDBInformationWith(NOW.plusDays(361)), INDEXER),
                PostFixed.withIncomeTax(4L, someCDBInformationWith(NOW.plusDays(181)), INDEXER),
                PostFixed.withIncomeTax(5L, someCDBInformationWith(NOW), INDEXER))
                        .map(Bond::incomeTaxRate).collect(Collectors.toList());

        assertEquals(List.of(0.0, 0.15, 0.175, 0.20, 0.225), incomeTaxRates);
    }

    @Test
    public void throwsInvalidEntityExceptionWhenPassInvalidValueObjects() {
        var exception = assertThrows(Entity.InvalidEntity.class, () ->
                PostFixed.withIncomeTax(123L, someInformationWithAllFieldsInvalid(), INDEXER));

        assertEquals("post-fixed bond", exception.errors().context());
        assertThat(exception.errors().reasons())
                .hasSize(4)
                .containsAll(List.of(
                        "bond name must be in ['CDB', 'TESOURO']",
                        "bond name must contain 3 to 17 letters",
                        "price must be greater 0.0",
                        "issuer name must contain 5 to 10 letters"));
    }

    private static final LocalDate NOW = LocalDate.now();
    private static final LocalDate MATURITY = NOW.plusDays(10);
    private static final Indexer INDEXER = IPCA.plus(percentage(5.0));
}
