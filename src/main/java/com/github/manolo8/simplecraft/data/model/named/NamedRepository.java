package com.github.manolo8.simplecraft.data.model.named;

import com.github.manolo8.simplecraft.data.connection.Database;
import com.github.manolo8.simplecraft.data.model.base.BaseRepository;

import java.sql.SQLException;

public abstract class NamedRepository<E extends NamedEntity,
        O extends NamedDTO,
        D extends NamedDAO<O>,
        C extends NamedCache<E, ?>,
        L extends NamedLoader<E, O>>
        extends BaseRepository<E, O, D, C, L> {

    public NamedRepository(Database database) throws SQLException {
        super(database);
    }

    public E findByName(String name) throws SQLException {
        name = name.toLowerCase();

        E entity = cache.getIfMatch(name);

        if (entity != null) return entity;

        O dto = dao.findByName(name);

        return fromDTO(dto);
    }
}
