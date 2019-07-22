package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.MealsUtil.getFilteredWithExcess;
import static ru.javawebinar.topjava.util.MealsUtil.getWithExcess;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class JspMealController {
    @Autowired
    private MealService service;

    @GetMapping("/meals")
    public String meals(Model model, HttpServletRequest request) {
        List<MealTo> meals = new ArrayList<>();
//        service.getAll(SecurityUtil.authUserId());
        String action = request.getParameter("action");
        if (action == null) {
            model.addAttribute("meals", getWithExcess(
                    service.getAll(authUserId()), authUserCaloriesPerDay()));
            return "meals";
        }
        switch (action) {
            case"filter":
            {
                LocalDate startDate=parseLocalDate(request.getParameter("startDate"));
                LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
                LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
                LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

                model.addAttribute("meals",
                        getWithFilter(startDate,endDate,startTime,endTime));

                return "meals";
            }
            case "create":
            case "update": {
                return "forward:meal";
            }

            case "delete": {
                try {
                    String mealId = request.getParameter("id");
                    if (mealId != null) {
                        service.delete(Integer.parseInt(mealId), authUserId());
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                return "redirect:meals";
            }
        }
        return "";
    }

    @PostMapping("/meals")
    public String addMeal(HttpServletRequest request) {
        String mealIdParam = request.getParameter("id");
        String descriptionParam = request.getParameter("description");
        String dateTimeParam = request.getParameter("dateTime");
        int calories = request.getParameter("calories").length() == 0 ? 0 : Integer.parseInt(request.getParameter("calories"));


        Meal meal = mealIdParam.length() == 0
                ? new Meal()
                : service.get(Integer.parseInt(request.getParameter("id")), authUserId());

        if (mealIdParam.length() != 0) {
            Meal storedMeal = service.get(Integer.parseInt(mealIdParam), authUserId());
        }


        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        meal.setDescription(request.getParameter("description"));
        meal.setDateTime(
                LocalDateTime.parse(request.getParameter("dateTime"))
        );

        service.update(meal, authUserId());

        return "redirect:meals";
    }

    @GetMapping("/meal")
    public String getMeal(HttpServletRequest request, Model model) {
        switch (request.getParameter("action")) {
            case "create": {
                Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", authUserCaloriesPerDay());
                model.addAttribute("meal", meal);
                break;
            }
            case "update": {
                String mealId = request.getParameter("id");
                if (mealId == null) {
                    throw new NotFoundException("Can't get id during meal's update");
                }
                model.addAttribute("meal", service.get(Integer.parseInt(mealId), authUserId()));
                break;
            }
        }

        return "mealForm";
    }

    @PostMapping("/meal")
    public String updateMeal(HttpServletRequest request, Model model) {
        String s = "";
        return "mealForm";
    }

    private List<MealTo> getWithFilter(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime){
        List<Meal> meals= service.getBetweenDates(startDate,endDate,authUserId());
        List<MealTo> filtered=getFilteredWithExcess(meals,authUserCaloriesPerDay(),startTime,endTime);
        return filtered;
    }

    public static LocalDate parseLocalDate(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalDate.parse(str);
    }

    public static LocalTime parseLocalTime(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalTime.parse(str);
    }
}
