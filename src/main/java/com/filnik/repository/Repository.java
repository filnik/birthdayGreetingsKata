package com.filnik.repository;

import java.util.ArrayList;

public interface Repository<T> {
    ArrayList<T> load();
    void store(T... t);
    void delete(T... t);
}