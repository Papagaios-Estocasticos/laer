package com.stochastic.parrots.laer.ut.domain.assets.indexers;

import com.stochastic.parrots.laer.domain.assets.indexers.CDI;
import com.stochastic.parrots.laer.domain.shared.Entity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.stochastic.parrots.laer.domain.assets.commons.Interest.percentage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CDITest {

    @Test
    public void createCdiPlusInterest() {
        var cdi = CDI.plus(percentage(3.0));

        assertEquals("CDI".hashCode(), cdi.id());
    }

    @Test
    public void createCdiTimesInterest() {
        var cdi = CDI.times(percentage(110));

        assertEquals("CDI".hashCode(), cdi.id());
    }

    @Test
    public void throwsInvalidEntityExceptionWhenPassInvalidInterest() {
        var exception = assertThrows(Entity.InvalidEntity.class, () -> {
            CDI.plus(percentage(-0.18));
        });

        assertEquals("CDI", exception.errors().context());
        assertThat(exception.errors().reasons())
                .hasSize(1)
                .containsAll(List.of("interest rate must be greater 0.0"));
    }

    @Test
    public void stringify() {
        var cdiPlus = CDI.plus(percentage(3.0));
        var cdiFactor = CDI.times(percentage(110));

        assertEquals("CDI +3.00%", cdiPlus.toString());
        assertEquals("110.00% CDI", cdiFactor.toString());
    }
}
