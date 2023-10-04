package com.stochastic.parrots.laer.domain.assets.bonds;

import com.stochastic.parrots.laer.domain.assets.commons.*;
import com.stochastic.parrots.laer.domain.shared.ValueObject;

import java.time.LocalDate;

public class Information extends ValueObject {
    private static final String CONTEXT = "bond information";

    public final String name;
    public final Issuer issuer;
    public final Price price;
    public final LocalDate maturity;

    protected Information(Builder builder) {
        super(CONTEXT);
        this.name = builder.name;
        this.issuer = builder.issuer;
        this.price = Price.of(builder.price);
        this.maturity = builder.maturity;
        validate();
    }

    private void validate() {
        validate(issuer, price);
        if(name.isBlank() || name.isEmpty()) {
            errors.because("bond name cannot be empty");
            return;
        }
        if(name.length() < 3 || name.length() > 17) {
            errors.because("bond name must contain 3 to 17 letters");
        }
    }

    public static class Builder {
        private String name;
        private Issuer issuer;
        private Double price;
        private LocalDate maturity;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder issuer(String issuer) {
            this.issuer = Issuer.of(issuer);
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder maturity(LocalDate maturity) {
            this.maturity = maturity;
            return this;
        }

        public Information build() {
            return new Information(this);
        }
    }
}