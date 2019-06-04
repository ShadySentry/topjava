package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> exceededMealList = getFilteredWithExceeded(mealList, LocalTime.of(1, 0), LocalTime.of(23, 0), 2000);
        exceededMealList.stream().forEach(e -> System.out.println(e.toString()));
        //        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        return mealList.stream()
                .sorted(Comparator.comparing(UserMeal::getDateTime))
                .filter(t -> TimeUtil.isBetween(t.getDateTime().toLocalTime(), startTime, endTime))
                .filter(meal -> mealList.stream()
                        .filter(mealsPerDay -> mealsPerDay.getDateTime().toLocalDate().equals(meal.getDateTime().toLocalDate())).
                                mapToInt(UserMeal::getCalories).sum() > caloriesPerDay)
                .map(UserMealsUtil::mealsConverter).collect(Collectors.toList());
/*
return mealList.stream()
        .sorted(Comparator.comparing(UserMeal::getDateTime))
        .filter(t -> TimeUtil.isBetween(t.getDateTime().toLocalTime(), startTime, endTime))
        .map(meal -> mealList.stream()
                .filter(mealsPerDay -> mealsPerDay.getDateTime().toLocalDate().equals(meal.getDateTime().toLocalDate())).
                        mapToInt(UserMeal::getCalories).sum() > caloriesPerDay ? mealsConverter(meal)).collect(Collectors.toList());*/

    }

    private static UserMealWithExceed mealsConverter(UserMeal userMeal) {
        return new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true);
    }
}
