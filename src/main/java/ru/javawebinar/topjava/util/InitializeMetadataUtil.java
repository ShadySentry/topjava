package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class InitializeMetadataUtil {
    private static final Logger logger = getLogger(InitializeMetadataUtil.class);

    private static List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 28, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 28, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 28, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2019, Month.MAY, 29, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2019, Month.MAY, 29, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2019, Month.MAY, 29, 20, 0), "Ужин", 499)
    );

    private static List<Meal> mealsWithExcess = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2019, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2019, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2019, Month.MAY, 31, 20, 0), "Ужин", 501)
    );

    public static List<Meal> getMeals() {
        logger.debug("got meals without excess");
//        logger.debug(meals.toString());

        return meals;
    }

    public static List<Meal> getMealsWithExcess() {
        logger.debug("got meals with excess");
//        logger.debug(mealsWithExcess.toString());

        return mealsWithExcess;
    }
}
