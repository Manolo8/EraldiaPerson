package com.github.manolo8.simplecraft.data.model.named;

import com.github.manolo8.simplecraft.data.cache.SaveCache;
import com.github.manolo8.simplecraft.data.model.base.Repository;

public class NamedCache<E extends NamedEntity, R extends Repository<E>> extends SaveCache<E, R> {

    public NamedCache(R repository) {
        super(repository);
    }

    public E getIfMatch(String name) {
        for (E entity : cached)
            if (entity.match(name)) {
                entity.refreshLastCheck();
                return entity;
            }

        return null;
    }
}
