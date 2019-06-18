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

import java.time.LocalDateTime;
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

    public void delete(Meal meal) {
        log.info("delete {}", meal);
        service.delete(meal, authUserId());
    }

    public Meal get(Meal meal) {
        log.info("update {}", meal);
        return service.get(meal, authUserId());
    }

    public List<Meal> getAll(int userId) {
        log.info("get all");
        return new ArrayList(service.getAll(userId));
    }

    public List<MealTo> getAllWithExcess() {
        log.info("getAllWithExcess");
        return service.getAllWithExcess(authUserId(), SecurityUtil.authUserCaloriesPerDay());
    }
//    public List<MealTo> getFilteredWithExcess(){
//        log.info("getFilteredWithExcess");
//        return service.getFilteredWithExcess(authUserId(),SecurityUtil.authUserCaloriesPerDay(),
//                LocalDateTime.of(2015, Month.MAY, 30, 10, 0).,
//                LocalDateTime.of(2016, Month.MAY, 31, 20, 0));
//    }


}