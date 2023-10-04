package com.stochastic.parrots.laer.domain.assets.indexers;

import com.stochastic.parrots.laer.domain.assets.commons.Interest;
import com.stochastic.parrots.laer.domain.shared.Entity;

public abstract class Indexer extends Entity {
    protected final Interest interest;

    protected Indexer(Long id, String context, Interest interest) {
        super(id, context);
        this.interest = interest;
        validate(interest);
    }

    public Interest interest() {
        return this.interest;
    }
}