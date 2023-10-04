package com.stochastic.parrots.laer.ut.domain.assets.bonds;

import com.stochastic.parrots.laer.domain.assets.bonds.Information;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static com.stochastic.parrots.laer.fixture.domain.assets.commons.InformationFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InformationTest {

    @Test
    public void createInformation() {
        var now = LocalDate.now();
        var information = new Information.Builder()
                .name("some bond")
                .issuer("some bank")
                .maturity(now)
                .price(100.00).build();


        assertEquals("some bond", information.name);
        assertEquals("some bank", information.issuer.get());
        assertEquals(100.00, information.price.get());
        assertEquals(now, information.maturity);
        assertFalse(information.hasErrors());
    }


    @Test
    public void createInformationWithErrorWhenPassEmptyName() {
        var information = new Information.Builder()
                .name("")
                .issuer("some issuer")
                .price(2.0)
                .build();

        assertEquals("bond information", information.errors().context());
        assertTrue(information.hasErrors());
        assertThat(information.errors().reasons())
                .hasSize(1)
                .contains("bond name cannot be empty");
    }


    @Test
    public void createInformationWithErrorWhenPassInvalidIssuer() {
        var information = new Information.Builder()
                .name("some name")
                .issuer("test")
                .price(2.0)
                .build();

        assertEquals("bond information", information.errors().context());
        assertTrue(information.hasErrors());
        assertThat(information.errors().reasons())
                .hasSize(1)
                .contains("issuer name must contain 5 to 10 letters");
    }

    @Test
    public void createInformationWithErrorWhenPassInvalidPrice() {
        var information = new Information.Builder()
                .name("some name")
                .issuer("some issuer")
                .price(-1.0)
                .build();

        assertEquals("bond information", information.errors().context());
        assertTrue(information.hasErrors());
        assertThat(information.errors().reasons())
                .hasSize(1)
                .contains("price must be greater 0.0");
    }

    @Test
    public void createInformationWithErrorWhenPassInvalidPriceIssuerAndName() {
        var information = someInformationWithAllFieldsInvalid();

        assertEquals("bond information", information.errors().context());
        assertTrue(information.hasErrors());
        assertThat(information.errors().reasons())
                .hasSize(3)
                .containsAll(List.of(
                        "price must be greater 0.0",
                        "issuer name must contain 5 to 10 letters",
                        "bond name must contain 3 to 17 letters"));
    }
}
