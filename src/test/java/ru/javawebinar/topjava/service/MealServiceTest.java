package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(100100, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
        Meal createdMeal = service.create(newMeal, USER_ID);
        newMeal.setId(createdMeal.getId());
        assertMatch(newMeal, MEAL_1);
    }

    @Test
    public void get() {
        Meal newMeal = service.get(MEAL_1.getId(), USER_ID);
        assertMatch(newMeal, MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getWithWrongUserCheck() {
        Meal newMeal = service.get(MEAL_1.getId(), WRONG_USER_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> meals = service.getBetweenDates(START_DATE, LocalDate.now(), USER_ID);
        assertMatch(meals, USER_MEALS);
    }

    @Test
    public void getBetweenDatesWithWrongUserCheck() {
        List<Meal> meals = service.getBetweenDates(START_DATE, LocalDate.now(), 3);
        assertMatch(meals, EMPTY_LIST);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> meals = service.getBetweenDateTimes(START_DATE_TIME, LocalDateTime.now(), USER_ID);
        assertMatch(meals, USER_MEALS);
    }

    @Test
    public void getBetweenDateTimesWithWrongUserCheck() {
        List<Meal> meals = service.getBetweenDateTimes(START_DATE_TIME, LocalDateTime.now(), 3);
        assertMatch(meals, EMPTY_LIST);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, USER_MEALS);
    }

    @Test
    public void getAllWithWrongUserCheck() {
        List<Meal> meals = service.getAll(3);
        assertMatch(meals, EMPTY_LIST);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL_1.getId(), USER_ID);
        List<Meal> meals = USER_MEALS.stream()
                .filter(meal -> meal.getId() != MEAL_1.getId())
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        assertMatch(service.getAll(USER_ID), meals);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNoId() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWithWrongUserCheck() throws Exception {
        service.delete(MEAL_1.getId(), WRONG_USER_ID);
    }

    @Test
    public void update() {
        service.update(MEAL_FOR_UPDATE, USER_ID);
        assertMatch(service.getAll(USER_ID), UPDATED_USER_MEALS);

    }

    @Test(expected = NotFoundException.class)
    public void updateNoId() {
        service.update(MEAL_FOR_UPDATE_WITH_WRONG_ID, USER_ID);

    }

    @Test(expected = NotFoundException.class)
    public void updateWrongUserCheck() {
        service.update(MEAL_FOR_UPDATE, WRONG_USER_ID);
    }
}