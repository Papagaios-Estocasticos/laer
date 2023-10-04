package com.stochastic.parrots.laer.domain.assets.indexers;

import com.stochastic.parrots.laer.domain.assets.commons.Interest;

public class CDI extends Indexer {
    private static final String NAME = "CDI";
    private final Operation operation;

    private CDI(Interest interest, Operation operation) {
        super(((long) NAME.hashCode()), NAME, interest);
        this.operation = operation;
    }

    public static CDI times(Interest interest) {
        return new CDI(interest, Operation.TIMES);
    }

    public static CDI plus(Interest interest) {
        return new CDI(interest, Operation.PLUS);
    }

    @Override
    public String toString() {
        if(operation == Operation.PLUS) {
            return  String.format("%s +%s", NAME, interest);
        }
        return  String.format("%s %s", interest, NAME);
    }

    private enum Operation {
        PLUS, TIMES
    }
}
