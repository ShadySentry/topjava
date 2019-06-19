package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private MealService service;
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    public MealRestController(MealService service) {
        log.info("MealService created");
        this.service = service;
    }

    public void create(Meal meal) {
        log.info("create {}", meal);
        service.create(meal, authUserId());
    }

    public void update(Meal meal) {
        log.info("update {}", meal);
        service.create(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public Meal get(int mealId) {
        log.info("update {}", mealId);
        return service.get(mealId, authUserId());
    }

    public List<Meal> getAll() {
        log.info("get all");
        return new ArrayList(service.getAll(authUserId()));
    }

    public List<MealTo> getAllWithExcess() {
        log.info("getAllWithExcess");
        return service.getAllWithExcess(authUserId(), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo>getWithExcess(){
        return MealsUtil.getWithExcess(getAll(),SecurityUtil.authUserCaloriesPerDay());
    }
    public List<MealTo> getFilteredWithExcess(LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime){
        log.info("getFilteredWithExcess");
        return service.getFilteredWithExcess(authUserId(),SecurityUtil.authUserCaloriesPerDay(),fromDate,toDate, fromTime,toTime);
    }


}