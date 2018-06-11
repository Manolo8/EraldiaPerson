package com.github.manolo8.simplecraft.data.model.base;

import java.sql.SQLException;

public interface Repository<E extends BaseEntity> {

    E findOne(Integer id) throws SQLException;

    void save(E entity) throws SQLException;

    void delete(E entity) throws SQLException;
}
