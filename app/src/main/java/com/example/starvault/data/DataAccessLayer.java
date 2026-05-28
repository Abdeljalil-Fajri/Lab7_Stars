package com.example.starvault.data;

import java.util.List;

public interface DataAccessLayer<T> {
    boolean insert(T item);
    boolean refresh(T item);
    boolean remove(T item);
    T getById(int id);
    List<T> getAll();
}