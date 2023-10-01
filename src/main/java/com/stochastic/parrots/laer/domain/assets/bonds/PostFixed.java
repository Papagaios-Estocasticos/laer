package com.stochastic.parrots.laer.domain.assets.bonds;

import com.stochastic.parrots.laer.domain.assets.commons.Issuer;
import com.stochastic.parrots.laer.domain.assets.indexers.Indexer;

public class PostFixed extends Bond {
    private static final String CONTEXT = "post-fixed bond";
    private final Indexer indexer;

    private PostFixed(Builder builder) {
        super(builder.id, builder.information, CONTEXT);
        this.indexer = builder.indexer;
        validate(information);
    }

    private static PostFixed withoutIncomeTax(Long id, Information.Builder information, Indexer indexer) {
        return new Builder()
                .id(id)
                .with(information.incomeTaxExemption())
                .indexer(indexer).build();
    }

    public static PostFixed lca(Long id, Information.Builder information, Indexer indexer) {
        return withoutIncomeTax(id, information.name("LCA"), indexer);
    }

    public static PostFixed lci(Long id, Information.Builder information, Indexer indexer) {
        return withoutIncomeTax(id, information.name("LCI"), indexer);
    }

    public static PostFixed cra(Long id, Information.Builder information, Indexer indexer) {
        return withoutIncomeTax(id, information.name("CRA"), indexer);
    }

    public static PostFixed cri(Long id, Information.Builder information, Indexer indexer) {
        return withoutIncomeTax(id, information.name("CRI"), indexer);
    }

    public static PostFixed cdb(Long id, Information.Builder information, Indexer indexer) {
        return new Builder()
                .id(id)
                .with(information.name("CDB").notIncomeTaxExemption())
                .indexer(indexer).build();
    }

    public static PostFixed treasury(Long id, Information.Builder information, Indexer indexer) {
        return new Builder()
                .id(id)
                .with(information.name("Tesouro").notIncomeTaxExemption().issuer(Issuer::government))
                .indexer(indexer).build();
    }

    public String name() {
        if(issuer().equals(Issuer.government())) {
            return String.format("%s %s %d", information.name, indexer, maturity().getYear());
        }
        return String.format("%s %s %d %s", information.name, information.issuer, maturity().getYear(), indexer);
    }

    public Indexer indexer() {
        return this.indexer;
    }

    public static class Builder {
        private Long id;
        private Information information;
        private Indexer indexer;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder with(Information.Builder builder) {
            this.information = builder.build();
            return this;
        }

        private Builder indexer (Indexer indexer) {
            this.indexer = indexer;
            return this;
        }

        public PostFixed build() {
            return new PostFixed(this);
        }
    }
}
