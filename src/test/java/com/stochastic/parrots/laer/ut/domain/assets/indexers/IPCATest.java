package com.stochastic.parrots.laer.ut.domain.assets.indexers;

import com.stochastic.parrots.laer.domain.assets.indexers.IPCA;
import com.stochastic.parrots.laer.domain.shared.Entity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.stochastic.parrots.laer.domain.assets.commons.Interest.percentage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class IPCATest {

    @Test
    public void createIpcaPlusInterest() {
        var ipca = IPCA.plus(percentage(5));

        assertEquals("IPCA".hashCode(), ipca.id());
        assertEquals(percentage(5), ipca.interest());
    }

    @Test
    public void throwsInvalidEntityExceptionWhenPassInvalidInterest() {
        var exception = assertThrows(Entity.InvalidEntity.class, () -> {
            IPCA.plus(percentage(-5));
        });

        assertEquals("IPCA", exception.errors().context());
        assertThat(exception.errors().reasons())
                .hasSize(1)
                .containsAll(List.of("interest rate must be greater 0.0"));
    }

    @Test
    public void stringify() {
        var ipca = IPCA.plus(percentage(5));
        assertEquals("IPCA+ 5.00%", ipca.toString());
    }
}
