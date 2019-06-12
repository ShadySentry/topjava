package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.util.concurrent.ConcurrentSkipListMap;

public class InMemoryDataUtil {
    private static ConcurrentSkipListMap<Long, Meal> meals = null;

    private InMemoryDataUtil() {
    }

    public ConcurrentSkipListMap<Long, Meal> getInstance() {
        if (meals == null) {
            meals = new ConcurrentSkipListMap<>();
        }
        return meals;
    }

}
