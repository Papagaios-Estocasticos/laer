package com.stochastic.parrots.laer.domain.assets.bonds;

import com.stochastic.parrots.laer.domain.assets.commons.Interest;

public class PreFixed extends Bond {
    private static final String CONTEXT = "pre-fixed bond";
    private final Interest interest;

    private PreFixed(Builder builder) {
        super(builder.id, builder.information, builder.isIncomeTaxExempt, CONTEXT);
        this.interest = builder.interest;
        validate();
    }

    public static PreFixed withoutIncomeTax(Long id, Information information, Interest interest) {
        return new Builder()
                .id(id)
                .with(information)
                .incomeTaxExemption()
                .interest(interest).build();
    }

    public static PreFixed withIncomeTax(Long id, Information information, Interest interest) {
        return new Builder()
                .id(id)
                .with(information)
                .notIncomeTaxExemption()
                .interest(interest).build();
    }

    private void validate() {
        validate(information, interest);
    }

    public String name() {
        return String.format("%s %d %s", information.name, maturity().getYear(), interest());
    }

    public Interest interest() {
        return this.interest;
    }

    private static class Builder {
        private Long id;
        private Information information;
        private Interest interest;
        private boolean isIncomeTaxExempt;

        private Builder id(Long id) {
            this.id = id;
            return this;
        }

        private Builder with(Information information) {
            this.information = information;
            return this;
        }

        private Builder interest(Interest interest) {
            this.interest = interest;
            return this;
        }

        private Builder incomeTaxExemption() {
            this.isIncomeTaxExempt = true;
            return this;
        }

        private Builder notIncomeTaxExemption() {
            this.isIncomeTaxExempt = false;
            return this;
        }

        private PreFixed build() {
            return new PreFixed(this);
        }
    }
}
