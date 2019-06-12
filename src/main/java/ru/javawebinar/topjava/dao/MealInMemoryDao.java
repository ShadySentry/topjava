package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealInMemoryDao implements MealDao {
    private static ConcurrentSkipListMap<Long, Meal> meals = null;
    private static final Logger log = getLogger(MealInMemoryDao.class);

    public MealInMemoryDao() {
        if (meals == null) {
            meals = new ConcurrentSkipListMap<>();
        }
    }


    public long create(Meal meal) {
        long pKey = -1;
        if (!meals.containsValue(meal)) {
            meals.put(meals.lastKey() + 1, meal);
            pKey = meals.lastKey();
        } else if (meals.containsValue(meal)) {
            pKey = getKey(meal);
        }
        return pKey;
    }


    public Meal read(long id) {
        return meals.get(id);
    }


    public Meal update(Meal meal) {
        Meal updatedMeal = null;
        if (meal != null && meal.getId() > 0 && meals.containsKey(meal.getId())) {
            meals.replace(meal.getId(), meal);
        }
        return updatedMeal;
    }


    public boolean delete(Meal meal) {
        if (meal != null && meals.containsKey(meal.getId())) {
            return meals.remove(meal.getId(), meal);
        }
        return false;
    }

    long getKey(Meal meal) {
        if (!meals.containsValue(meal)) {
            return -1;
        }
        return meals.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), meal))
                .map(ConcurrentSkipListMap.Entry::getKey)
                .collect(Collectors.toList()).get(0);
    }
}
