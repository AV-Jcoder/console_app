package org.afoninav.repository;

import java.util.List;

public interface GenericRepository<T,ID> {
    boolean create(T t);
    T readById(ID id);
    boolean update(T t);
    boolean deleteByID(ID id);
    List<T> readAll();
}
