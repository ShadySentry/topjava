package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

//@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    public void setRepository(MealRepository repository) {
        this.repository = repository;
    }

    //    @Autowired
//    public MealServiceImpl(MealRepository repository){
//        this.repository = repository;
//    }

    @Override
    public Meal create(Meal meal) {
        return null;
    }

    @Override
    public void update(Meal meal) throws NotFoundException {

    }

    @Override
    public void delete(Meal meal) throws NotFoundException {

    }

    @Override
    public Meal get(Meal meal) throws NotFoundException {
        return null;
    }

    @Override
    public List<Meal> getAll() {
        return null;
    }
}