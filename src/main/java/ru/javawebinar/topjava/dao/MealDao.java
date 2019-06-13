package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Optional;

public interface MealDao {
    long create(Meal meal);

    Meal read(long id);

    Meal update(Meal meal);

    boolean delete(Meal meal);

    List<Meal> getAll();



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
