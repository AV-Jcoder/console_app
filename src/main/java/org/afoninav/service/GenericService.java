package org.afoninav.service;

import java.util.List;

public interface GenericService<T,ID> {
    boolean create(T t);
    T readById(ID id);
    List<T> readAll();
    boolean update(T t);
    boolean deleteById(ID id);
}
