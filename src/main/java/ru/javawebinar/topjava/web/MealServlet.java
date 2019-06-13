package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealInMemoryDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.InitializeMetadataUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        Map parameters = req.getParameterMap();
        if (parameters.containsKey("edit")) {
            Meal meal = convertToMeal(parameters);
            if (meal != null) {
                meals.update(meal);
            }
        } else if (parameters.containsKey("delete")) {

        }

        super.doPost(req, resp);
    }

    private List<MealTo> getAllMeals() {
        List<Meal> allMeals = meals.getAll();
        allMeals.addAll(InitializeMetadataUtil.getMealsWithExcess());

        return MealsUtil.getFilteredWithExcessInOnePass2(allMeals, LocalTime.of(0, 1), LocalTime.of(23, 59), 2000);
    }

    private Meal convertToMeal(Map parameters) {
        if (!(parameters.containsKey("id") && parameters.containsKey("date") && parameters.containsKey("description") && parameters.containsKey("calories"))) {
            return null;
        }

        final long id;
        final LocalDateTime date;
        final String[] description = new String[1];
        final int calories;

        parameters.forEach((k, v) -> {
            switch ((String) k) {
                case "id":
                    id = Long.parseLong((String) v);
                    break;
                case "date":
                    description[0] = (String) v;
                    break;
                case "description":

            }
        });

        long id = Long.parseLong((String) (parameters.get("id")));
        LocalDateTime date = LocalDateTime.parse((String) parameters.get("date"));
        String description = (String) parameters.get("description");
        int calories = Integer.parseInt((String) parameters.get("calories"));
        return new Meal(id, date, description, calories);
    }

}
