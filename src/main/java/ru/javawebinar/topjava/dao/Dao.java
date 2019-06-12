package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Optional;

public interface Dao<T, PK> {
    long create(Meal meal);

    T read(PK id);

    T update(T t);

    void delete(T t);

//    Optional<T> get(long id);
//
//    List<T> getAll();
//
//    void save(T t);
//
//    void update(T t, String[] params);
//
//    void delete(T t);
}
