package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealService {
    Meal create(Meal meal, int userId);

    void update(Meal meal, int userId) throws NotFoundException;

    void delete(int mealId, int userId) throws NotFoundException;

    Meal get(int mealId, int userId) throws NotFoundException;

    List<MealTo> getAll(int userId);

    List<MealTo> getAllWithExcess(int userId, int caloriesPerDay);

    List<MealTo> getFilteredWithExcess(int userId, int caloriesPerDay, LocalDate fromDate, LocalDate toDate, LocalTime startTime, LocalTime endTime);


}