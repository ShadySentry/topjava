package ru.javawebinar.topjava.web;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.getWithExcess;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class JspMealController {
    @Autowired
    private MealService service;

    @GetMapping("/meals")
    public String meals(Model model,HttpServletRequest request){
        List<MealTo> meals = new ArrayList<>();
//        service.getAll(SecurityUtil.authUserId());
        model.addAttribute("meals",getWithExcess(
                service.getAll(authUserId()),authUserCaloriesPerDay()));
        return "meals";
    }

    @PostMapping("/meals")
    public String addMeal(HttpServletRequest request){
        String action = request.getParameter("action");
        return "meals";
    }



}
