package com.stochastic.parrots.laer.domain.assets.bonds;

import com.stochastic.parrots.laer.domain.assets.commons.Interest;
import com.stochastic.parrots.laer.domain.assets.commons.Issuer;

public class PreFixed extends Bond {
    private static final String CONTEXT = "pre-fixed bond";
    private final Interest interest;

    private PreFixed(Builder builder) {
        super(builder.id, builder.information, CONTEXT);
        this.interest = builder.interest;
        validate();
    }

    private static PreFixed withoutIncomeTax(Long id, Information.Builder information, Interest interest) {
        return new Builder()
                .id(id)
                .with(information.incomeTaxExemption().build())
                .interest(interest).build();
    }

    public static PreFixed lca(Long id, Information.Builder information, Interest interest) {
        return withoutIncomeTax(id, information.name("LCA"), interest);
    }

    public static PreFixed lci(Long id, Information.Builder information, Interest interest) {
        return withoutIncomeTax(id, information.name("LCI"), interest);
    }

    public static PreFixed cra(Long id, Information.Builder information, Interest interest) {
        return withoutIncomeTax(id, information.name("CRA"), interest);
    }

    public static PreFixed cri(Long id, Information.Builder information, Interest interest) {
        return withoutIncomeTax(id, information.name("CRI"), interest);
    }

    public static PreFixed cdb(Long id, Information.Builder information, Interest interest) {
        return new Builder()
                .id(id)
                .with(information
                        .name("CDB")
                        .notIncomeTaxExemption()
                        .build())
                .interest(interest).build();
    }

    public static PreFixed treasury(Long id, Information.Builder information, Interest interest) {
        return new Builder()
                .id(id)
                .with(information
                        .name("Tesouro Préfixado")
                        .notIncomeTaxExemption()
                        .issuer(Issuer::government)
                        .build())
                .interest(interest).build();
    }

    private void validate() {
        validate(information, interest);
    }

    public String name() {
        if (issuer().equals(Issuer.government())) {
            return String.format("%s %d %s", information.name, maturity().getYear(), interest());
        }
        return String.format("%s %s %d %s", information.name, information.issuer, maturity().getYear(), interest());
    }

    public Interest interest() {
        return this.interest;
    }

    private static class Builder {
        private Long id;
        private Information information;
        private Interest interest;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder with(Information information) {
            this.information = information;
            return this;
        }

        public Builder interest(Interest interest) {
            this.interest = interest;
            return this;
        }

        public PreFixed build() {
            return new PreFixed(this);
        }
    }
}
