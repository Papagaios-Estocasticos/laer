package com.stochastic.parrots.laer.domain.assets.indexers;

import com.stochastic.parrots.laer.domain.assets.commons.Interest;

public class IPCA extends Indexer {
    private static final String NAME = "IPCA";

    private IPCA(Interest interest) {
        super(((long) NAME.hashCode()), NAME, interest);
    }

    public static IPCA plus(Interest interest) {
        return new IPCA(interest);
    }

    @Override
    public String toString() {
        return  String.format("%s+ %s", NAME, interest);
    }
}