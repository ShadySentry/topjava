package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public interface MealService {
    Meal create(Meal meal);

    void update(Meal meal) throws NotFoundException;

    void delete(Meal meal) throws NotFoundException;

    Meal get(Meal meal) throws NotFoundException;

    List<Meal> getAll();
}