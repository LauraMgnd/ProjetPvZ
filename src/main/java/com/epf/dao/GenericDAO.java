package com.epf.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, ID> {
    void create(T entity);
    Optional<T> readById(ID id);
    List<T> readAll();
    void update(T entity);
    void deleteById(ID id);
}
