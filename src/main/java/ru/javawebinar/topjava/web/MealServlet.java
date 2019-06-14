package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealInMemoryDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.InitializeMetadataUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    static MealDao meals = new MealInMemoryDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirected to meals");

        try {

            getServletContext().setAttribute("meals", getAllMeals());
            getServletContext().getRequestDispatcher("/meals.jsp").forward(req, resp);

//            req.getRequestDispatcher("/meals.jsp").forward(req,resp);

//            resp.sendRedirect("meals.jsp");
        } catch (Exception ex) {
            log.debug(ex.toString());
            throw ex;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Map parameters = req.getParameterMap();
        if (parameters.containsKey("edit")) {
            Meal meal = convertToMeal(req);
            if (meal != null) {
                meals.update(meal);
            }
        } else if (parameters.containsKey("delete")) {
            meals.delete(Long.valueOf((String)req.getParameter("id")));
        }

        getServletContext().setAttribute("meals",getAllMeals());
        getServletContext().getRequestDispatcher("/meals.jsp").forward(req, resp);
//        RequestDispatcher dispatcher = req.getRequestDispatcher("/meals.jsp");
//        req.setAttribute("meals",getAllMeals());
//        super.doPost(req, resp);
    }

    private List<MealTo> getAllMeals() {
        List<Meal> allMeals = meals.getAll();
        allMeals.addAll(InitializeMetadataUtil.getMealsWithExcess());

        return MealsUtil.getFilteredWithExcessInOnePass2(allMeals, LocalTime.of(0, 1), LocalTime.of(23, 59), 2000);
    }

    private Meal convertToMeal(HttpServletRequest parameters) {

        try {

            long id =Long.parseLong((String)parameters.getParameter("id"));
            LocalDateTime date = LocalDateTime.parse((String) parameters.getParameter("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String description=(String) parameters.getParameter("description");
            int calories=Integer.parseInt((String) parameters.getParameter("calories"));

            return new Meal(id,date,description,calories);
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
