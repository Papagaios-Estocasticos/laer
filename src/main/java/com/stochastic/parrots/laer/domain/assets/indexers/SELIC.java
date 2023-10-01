package com.stochastic.parrots.laer.domain.assets.indexers;

import com.stochastic.parrots.laer.domain.assets.commons.Interest;

public class SELIC extends Indexer {
    private static final String NAME = "SELIC";

    private SELIC(Interest interest) {
        super(((long) NAME.hashCode()), NAME, interest);
    }

    public static SELIC plus(Interest interest) {
        return new SELIC(interest);
    }

    @Override
    public String toString() {
        return  String.format("%s+ %s", NAME, interest);
    }
}
