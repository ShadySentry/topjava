package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.InitializeMetadataUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirected to meals");

        try{

            getServletContext().setAttribute("meals", getAllMeals());
            getServletContext().getRequestDispatcher("/meals.jsp").forward(req, resp);

//            req.getRequestDispatcher("/meals.jsp").forward(req,resp);

//            resp.sendRedirect("meals.jsp");
        }
        catch (Exception ex){
            log.debug(ex.toString());
            throw ex;
        }
    }

    private List<MealTo> getAllMeals() {
        List<Meal> allMeals = new ArrayList<>(InitializeMetadataUtil.getMeals());
        allMeals.addAll(InitializeMetadataUtil.getMealsWithExcess());

        return MealsUtil.getFilteredWithExcessInOnePass2(allMeals, LocalTime.of(0, 1), LocalTime.of(23, 59), 2000);
    }

}
