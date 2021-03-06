package com.github.manolo8.simplecraft.data.cache;

import com.github.manolo8.simplecraft.data.model.base.BaseEntity;
import com.github.manolo8.simplecraft.data.model.base.Repository;

import java.sql.SQLException;

public class SaveCache<E extends BaseEntity, R extends Repository<E>> extends Cache<E> {

    private R repository;

    public SaveCache(R repository) {
        this.repository = repository;
    }

    @Override
    public void saveModified(E entity) {
        super.saveModified(entity);

        try {
            repository.save(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unloaded(E entity) {
        super.unloaded(entity);

        try {
            if (entity.isRemoved())
                repository.delete(entity);
            else if (entity.isModified())
                repository.save(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}