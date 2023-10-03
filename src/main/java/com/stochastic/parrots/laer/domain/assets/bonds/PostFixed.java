package com.stochastic.parrots.laer.domain.assets.bonds;

import com.stochastic.parrots.laer.domain.assets.indexers.Indexer;

public class PostFixed extends Bond {
    private static final String CONTEXT = "post-fixed bond";
    private final Indexer indexer;

    private PostFixed(Builder builder) {
        super(builder.id, builder.information, builder.isIncomeTaxExempt, CONTEXT);
        this.indexer = builder.indexer;
        validate(information);
    }

    public static PostFixed withoutIncomeTax(Long id, Information information, Indexer indexer) {
        return new Builder()
                .id(id)
                .with(information)
                .incomeTaxExemption()
                .indexer(indexer).build();
    }


    public static PostFixed withIncomeTax(Long id, Information information, Indexer indexer) {
        return new Builder()
                .id(id)
                .with(information)
                .notIncomeTaxExemption()
                .indexer(indexer).build();
    }

    public String name() {
        return String.format("%s %d %s", information.name, maturity().getYear(), indexer);
    }

    public Indexer indexer() {
        return this.indexer;
    }

    public static class Builder {
        private Long id;
        private Information information;
        private Indexer indexer;
        private boolean isIncomeTaxExempt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder with(Information information) {
            this.information = information;
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

        private Builder indexer (Indexer indexer) {
            this.indexer = indexer;
            return this;
        }

        public PostFixed build() {
            return new PostFixed(this);
        }
    }
}
