package com.stochastic.parrots.laer.fixture.domain.assets.commons;

import com.stochastic.parrots.laer.domain.assets.bonds.Information;
import com.stochastic.parrots.laer.domain.assets.commons.Issuer;

import java.time.LocalDate;

public class InformationFixture {
    private InformationFixture() {
        // fixture class
    }

    public static Information.Builder someInformation() {
        return new Information.Builder()
                .name("some bond")
                .issuer(() -> Issuer.bank("some bank"))
                .price(1.0)
                .maturity(LocalDate.now())
                .incomeTaxExemption();
    }

    public static Information.Builder someInformationWithIncomeTaxException() {
        return someInformation()
                .incomeTaxExemption();
    }

    public static Information.Builder someInformationWithoutIncomeTaxException() {
        return someInformation()
                .notIncomeTaxExemption();
    }

    public static Information.Builder someInvalidInformation() {
        return someInformation()
                .price(-1.0)
                .issuer(() -> Issuer.bank("test"))
                .name("t");
    }
}
