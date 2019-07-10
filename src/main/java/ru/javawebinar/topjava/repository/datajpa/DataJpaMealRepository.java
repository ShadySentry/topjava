package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class DataJpaMealRepository implements MealRepository {

    private static final Sort SORT_DATE = new Sort(Sort.Direction.DESC, "dateTime");

    @Autowired
    private CrudMealRepository crudRepository;

//    @PersistenceContext
//    private EntityManager em;

//    @Autowired
//    private DataJpaUserRepository userRepository;

    @Autowired
    private UserService userService;

    @Transactional
    @Modifying
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        if(meal.isNew()) {
            meal.setUser(userService.get(userId));

//            meal.setUser(userRepository.get(userId));
        }else{
            Meal savedMeal = crudRepository.findById(meal.getId()).orElse(null);
            savedMeal.setUser(savedMeal.getUser());
        }

        return crudRepository.save(meal);
    }

    @Transactional
    @Modifying
    public boolean delete(int id, int userId) {
        Meal storedMeal = crudRepository.findById(id).orElse(null);
        if (storedMeal != null) {
            if (storedMeal.getUser().getId() != userId) {
                return false;
            }
        }
        return crudRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal storedMeal = crudRepository.findById(id).orElse(null);
        if (storedMeal != null && storedMeal.getUser() != null) {
            if (storedMeal.getUser().getId() != userId) {
                return null;
            }
        }
        return storedMeal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> storedMeals=crudRepository.findAll(SORT_DATE);
        return storedMeals.stream().filter(m -> m.getUser().getId() == userId).collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository.getBetween(startDate, endDate, userId);
    }
}
