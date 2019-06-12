package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.SQLDbUtil;

import java.sql.*;
import java.util.Arrays;

import static org.slf4j.LoggerFactory.getLogger;

public class MealToDao_ {
    private Connection connection;
    private static final Logger log = getLogger(MealToDao_.class);

    public MealToDao_(){
        connection = SQLDbUtil.getConnection();
        try {
            log.debug("Connected to " + connection.getSchema());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMeal(Meal meal){

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into meals(date ,Description, Calories) values (?,?,?)");
//            preparedStatement.setDate(1, new Date(meal.getDateTime().atZone(ZoneId.systemDefault()).toString()));
        } catch (SQLException e) {
            log.error(e.getMessage());
            Arrays.stream(e.getStackTrace()).forEach(element->log.error(element.toString()));

            e.printStackTrace();
        }
    }
}
