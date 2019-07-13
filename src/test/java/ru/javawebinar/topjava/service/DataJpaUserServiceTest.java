package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.UserTestData.getUserWithMeals;

@ActiveProfiles(profiles = "datajpa")
public class DataJpaUserServiceTest extends UserServiceTest {
//    @Autowired
//    UserService service;
    @Test
    public void getWithMeals() throws Exception{
        User user = service.getWithMeals(USER_ID);
        assertMatch(user,getUserWithMeals());
        MealTestData.assertMatch(user.getMeals(),getUserWithMeals().getMeals());
    }
}
