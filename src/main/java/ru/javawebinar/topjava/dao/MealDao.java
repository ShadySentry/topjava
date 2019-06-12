package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealDao implements Dao  {
    private static ConcurrentSkipListMap<Long, Meal> meals = null;
    private static final Logger log = getLogger(MealDao.class);

    public MealDao(){
        if(meals==null){
            meals = new ConcurrentSkipListMap<>();
        }
    }


    public long create(Meal meal) {
        long pKey = -1;
        if(!meals.containsValue(meal)){
            meals.put(meals.lastKey()+1,meal);
            pKey=meals.lastKey();
        }
        else if(meals.containsValue(meal)){
            pKey = getKey(meal);

        }
        return pKey;
    }


    public Object read(Object id) {
        return null;
    }


    public Object update(Object o) {
        return null;
    }


    public void delete(Object o) {

    }

    long getKey(Meal meal){
        if(!meals.containsValue(meal)){
            return -1;
        }
        return meals.entrySet()
                .stream()
                .filter(entry-> Objects.equals(entry.getValue(),meal))
                .map(ConcurrentSkipListMap.Entry::getKey)
                .collect(Collectors.toList()).get(0);
    }
}
