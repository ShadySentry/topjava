package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 100;
    public static final int LAST_KNOWN_MEAL_ID = START_SEQ + 115;

    public static final int USER_ID = START_SEQ;
    public static final int WRONG_USER_ID = START_SEQ + 1;

    public static final LocalDateTime START_DATE_TIME = LocalDateTime.of(2015, Month.MAY, 30, 10, 0);
    public static final LocalDate START_DATE = START_DATE_TIME.toLocalDate();

    public static final Meal MEAL_1 = new Meal(100100, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_2 = new Meal(100101, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_3 = new Meal(100102, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL_4 = new Meal(100103, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL_5 = new Meal(100104, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL_6 = new Meal(100105, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);//

    public static final Meal MEAL_OF_ADMIN_1 = new Meal(100110, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_OF_ADMIN_2 = new Meal(100111, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_OF_ADMIN_3 = new Meal(100112, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL_OF_ADMIN_4 = new Meal(100113, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL_OF_ADMIN_5 = new Meal(100114, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL_OF_ADMIN_6 = new Meal(100115, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);

    public static final Meal MEAL_FOR_UPDATE = new Meal(100100, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак-t", 555);

    public static final Meal MEAL_FOR_UPDATE_WITH_WRONG_ID = new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак-t", 555);

    public static final List<Meal> USER_MEALS = Stream.of(MEAL_1, MEAL_2, MEAL_3, MEAL_4, MEAL_5, MEAL_6)
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());

    public static final List<Meal> UPDATED_USER_MEALS = Stream.of(MEAL_FOR_UPDATE, MEAL_2, MEAL_3, MEAL_4, MEAL_5, MEAL_6)
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());

    public static final List<Meal> ADMIN_MEALS = Stream.of(MEAL_OF_ADMIN_1, MEAL_OF_ADMIN_2, MEAL_OF_ADMIN_3, MEAL_OF_ADMIN_4, MEAL_OF_ADMIN_5, MEAL_OF_ADMIN_6)
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());

    public static final List<Meal> EMPTY_LIST = new ArrayList<>();


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dateTime");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dateTime").isEqualTo(expected);
    }
}
