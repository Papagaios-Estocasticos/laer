package com.stochastic.parrots.laer.ut.domain.assets.indexers;

import static com.stochastic.parrots.laer.domain.assets.commons.Interest.percentage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.stochastic.parrots.laer.domain.assets.indexers.SELIC;
import com.stochastic.parrots.laer.domain.shared.Entity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SELICTest {

    @Test
    public void createSelicPlusInterest() {
        var selic = SELIC.plus(percentage(0.18));

        assertEquals("SELIC".hashCode(), selic.id());
        assertEquals(percentage(0.18), selic.interest());
    }

    @Test
    public void throwsInvalidEntityExceptionWhenPassInvalidInterest() {
        var exception = assertThrows(Entity.InvalidEntity.class, () -> {
           SELIC.plus(percentage(-0.18));
        });

        assertEquals("SELIC", exception.errors().context());
        assertThat(exception.errors().reasons())
                .hasSize(1)
                .containsAll(List.of("interest rate must be greater 0.0"));
    }

    @Test
    public void stringify() {
        var selic = SELIC.plus(percentage(0.18));
        assertEquals("SELIC+ 0.18%", selic.toString());
    }
}
