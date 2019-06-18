package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        log.info("create {}", meal);
        return repository.save(meal);
    }

    @Override
    public void update(Meal meal, int userId) throws NotFoundException {
        log.info("update {}", meal);
        repository.save(meal);
    }

    @Override
    public void delete(Meal meal, int userId) throws NotFoundException {
        log.info("delete {0} by user {1}", meal, userId);
        repository.delete(meal.getId(), userId);
    }

    @Override
    public Meal get(Meal meal, int userId) throws NotFoundException {
        log.info("get {0} by user {1}", meal, userId);
        return repository.get(meal.getId(), userId);
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
    public List<MealTo> getFilteredWithExcess(int userId, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return MealsUtil.getFilteredWithExcess(repository.getAll(userId), caloriesPerDay, startTime, endTime);
    }
}