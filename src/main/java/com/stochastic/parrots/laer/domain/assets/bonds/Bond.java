package com.stochastic.parrots.laer.domain.assets.bonds;

import com.stochastic.parrots.laer.domain.shared.Entity;
import com.stochastic.parrots.laer.domain.assets.commons.Issuer;
import com.stochastic.parrots.laer.domain.assets.commons.Price;

import java.time.LocalDate;

public abstract class Bond extends Entity {
    protected final Information information;

    protected Bond(Long id, Information information, String context) {
       super(id, context);
       this.information = information;
    }

    public abstract String name();

    public Issuer issuer() {
        return information.issuer;
    }

    public Price price() {
        return information.price;
    }

    public LocalDate maturity() {
        return information.maturity;
    }

    public double incomeTaxRate() {
        if(information.isIncomeTaxExempt) {
            return 0.0;
        }

        var now = LocalDate.now();
        if(information.maturity.minusDays(720).isAfter(now)) {
            return 0.15;
        }

        if(information.maturity.minusDays(360).isAfter(now)) {
            return 0.175;
        }

        if(information.maturity.minusDays(180).isAfter(now)) {
            return 0.20;
        }

        return 0.225;
    }

    public boolean isActive() {
        return information.maturity.isAfter(LocalDate.now());
    }
}
