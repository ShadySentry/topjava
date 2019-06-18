package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

//@Service
public class MealServiceImpl implements MealService {
    Logger log = LoggerFactory.getLogger(MealServiceImpl.class);
    private MealRepository repository;

    public void setRepository(MealRepository repository) {
        log.info("set up repository via setter {}", repository.toString());
        this.repository = repository;
    }

    //    @Autowired
    public MealServiceImpl(MealRepository repository) {
        log.info("set up repository via constructor {}", repository.toString());
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal) {
        log.info("create {}", meal);
        return repository.save(meal);
    }

    @Override
    public void update(Meal meal) throws NotFoundException {
        log.info("update {}", meal);
        repository.save(meal);
    }

    @Override
    public void delete(Meal meal) throws NotFoundException {
        log.info("delete {0} by user {1}", meal, authUserId());
        repository.delete(meal.getId(), authUserId());
    }

    @Override
    public Meal get(Meal meal) throws NotFoundException {
        log.info("get {0} by user {1}", meal, authUserId());
        return repository.get(meal.getId(),authUserId());
    }

    @Override
    public List<Meal> getAll() {
        log.info("get all by user {}", authUserId());
        return new ArrayList<>(repository.getAll(authUserId()));
    }
}