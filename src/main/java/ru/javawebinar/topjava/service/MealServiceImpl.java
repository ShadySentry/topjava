package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl implements MealService {
    Logger log = LoggerFactory.getLogger(MealServiceImpl.class);
    private MealRepository repository;

//    public void setRepository(MealRepository repository) {
//        log.info("set up repository via setter {}", repository.toString());
//        this.repository = repository;
//    }

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        log.info("set up repository via constructor {}", repository.toString());
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal, int userId) {
        if (meal.getUserId() == null) {
            meal.setUserId(userId);
        }
        log.info("create {}", meal);
        return repository.save(meal);
    }

    @Override
    public void update(Meal meal, int userId) throws NotFoundException {
        meal.setId(userId);
        log.info("update {}", meal);
        repository.save(meal);
    }

    @Override
    public void delete(int mealId, int userId) throws NotFoundException {
        log.info("delete {0} by user {1}", mealId, userId);
        repository.delete(mealId, userId);
    }

    @Override
    public Meal get(int mealId, int userId) throws NotFoundException {
        log.info("get {0} by user {1}", mealId, userId);
        return repository.get(mealId, userId);
    }

    @Override
    public ArrayList<MealTo> getAll(int userId) {
        log.info("get all by user {}", userId);
        return new ArrayList(repository.getAll(userId));
    }

    @Override
    public List<MealTo> getAllWithExcess(int userId, int caloriesPerDay) {
        log.info("get all by user {}", userId);
        return MealsUtil.getWithExcess(repository.getAll(userId), caloriesPerDay);
    }

    @Override
    public List<MealTo> getFilteredWithExcess(int userId, int caloriesPerDay, LocalDate fromDate, LocalDate toDate, LocalTime startTime, LocalTime endTime) {
        Collection<Meal> meals;
        if (fromDate != null && toDate != null) {
            meals = repository.getAllFiltered(userId, fromDate, toDate);
        } else {
            meals = repository.getAll(userId);
        }

        if (startTime != null && endTime != null) {
            return MealsUtil.getFilteredWithExcess(meals, caloriesPerDay, startTime, endTime);
        } else {
            return MealsUtil.getWithExcess(meals, caloriesPerDay);
        }
    }
}