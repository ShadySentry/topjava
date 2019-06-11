package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.InitializeMetadataUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirected to meals");

        try{

//            getServletContext().setAttribute("singleMeal", getSingleMeal());
            getServletContext().setInitParameter("singleMeal",getSingleMeal().toString());
            getServletContext().getRequestDispatcher("/meals.jsp").forward(req, resp);

//            req.getRequestDispatcher("/meals.jsp").forward(req,resp);

//            resp.sendRedirect("meals.jsp");
        }
        catch (Exception ex){
            log.debug(ex.toString());
            throw ex;
        }
//        super.doGet(req, resp);
    }

    private Meal getSingleMeal(){
        return InitializeMetadataUtil.getMeals().get(0);
    }
}
