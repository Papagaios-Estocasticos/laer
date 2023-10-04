package com.stochastic.parrots.laer.domain.assets.bonds;

import com.stochastic.parrots.laer.domain.shared.Entity;
import com.stochastic.parrots.laer.domain.assets.commons.Issuer;
import com.stochastic.parrots.laer.domain.assets.commons.Price;

import java.time.LocalDate;
import java.util.List;

public abstract class Bond extends Entity {
    private static final List<String> WITH_INCOME_TAX_NAMES = List.of("CDB", "TESOURO");
    private static final List<String> WITHOUT_INCOME_TAX_NAMES = List.of("LCI", "LCA", "CRI", "CRA");
    protected final Information information;
    protected final boolean isIncomeTaxExempt;

    protected Bond(Long id, Information information, boolean isIncomeTaxExempt, String context) {
       super(id, context);
       this.information = information;
       this.isIncomeTaxExempt = isIncomeTaxExempt;
       validate();
    }

    private void validate() {
        if(isIncomeTaxExempt && !WITHOUT_INCOME_TAX_NAMES.contains(information.name)) {
            errors.because("bond name must be in ['LCA', 'LCI', 'CRA', 'CRI']");
        } else if (!isIncomeTaxExempt && !WITH_INCOME_TAX_NAMES.contains(information.name)) {
            errors.because("bond name must be in ['CDB', 'TESOURO']");
        }
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

    public boolean haveIncomeTaxException() {
        return isIncomeTaxExempt;
    }

    public double incomeTaxRate() {
        if(isIncomeTaxExempt) {
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
