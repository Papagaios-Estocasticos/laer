package com.stochastic.parrots.laer.fixture.domain.assets.commons;

import com.stochastic.parrots.laer.domain.assets.bonds.Information;

import java.time.LocalDate;

public class InformationFixture {
    private InformationFixture() {
        // fixture class
    }

    public static Information someLCAInformationWith(LocalDate maturity) {
        return new Information.Builder()
                .name("LCA")
                .issuer("some bank")
                .price(1.0)
                .maturity(maturity)
                .build();
    }

    public static Information someCDBInformationWith(LocalDate maturity) {
        return new Information.Builder()
                .name("CDB")
                .issuer("some bank")
                .price(1.0)
                .maturity(maturity)
                .build();
    }


    public static Information someInformationWithAllFieldsInvalid() {
        return new Information.Builder()
                .price(-1.0)
                .issuer("test")
                .name("t")
                .maturity(LocalDate.now())
                .build();
    }
}
