package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/ajax/meals")
public class MealUIController extends AbstractMealController {

    @Override
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

//    @PutMapping
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void create(@RequestParam("id") Integer id,
//                       @RequestParam("dateTime") LocalDateTime dateTime,
//                       @RequestParam("description") String description,
//                       @RequestParam("calories") Integer calories) {
//        Meal meal = new Meal(dateTime, description, calories);
//        if (meal.isNew()) {
//            super.create(meal);
//        }
//
//    }


    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createPost(@RequestParam("id") Integer id,
                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                           @RequestParam("description") String description,
                           @RequestParam("calories") Integer calories) {
        Meal meal = new Meal();
        if (meal.isNew()) {
            super.create(meal);
        }

    }

//    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void create(@RequestParam("id") Integer id,
//                       @RequestParam("description") String description,
//                       @RequestParam("calories") Integer calories,
//                       @RequestParam("datetime") LocalDateTime dateTime) {
//        Meal meal = new Meal(dateTime, description, calories);
//        if (meal.isNew()) {
//            super.create(meal);
//        }
//
//    }

}
