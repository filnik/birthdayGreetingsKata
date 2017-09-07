package com.filnik.repository;

public interface Repository<T> {
    T load();
    void store(T t);
    void delete(T t);
}