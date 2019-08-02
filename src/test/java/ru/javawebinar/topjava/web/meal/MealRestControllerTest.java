package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.readFromJson;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

class MealRestControllerTest extends AbstractMealControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    void testDelete() throws Exception{
        mockMvc.perform(delete(REST_URL+MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(mealService.getAll(authUserId()), List.of(MEAL6,MEAL5,MEAL4,MEAL3,MEAL2));

    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL6,MEAL5,MEAL4,MEAL3,MEAL2,MEAL1));
    }

    @Test
    void testCreate() throws Exception{
        Meal expected = new Meal(LocalDateTime.now(),"newMeal",555);
        ResultActions actions = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isCreated());

        Meal returned = readFromJson(actions,Meal.class);
        expected.setId(returned.getId());

        assertMatch(returned,expected);
        assertMatch(mealService.getAll(authUserId()),List.of(expected,MEAL6,MEAL5,MEAL4,MEAL3,MEAL2,MEAL1));

    }

    @Test
    void testUpdate() throws Exception{
        Meal updated = new Meal(MEAL1.getId(),MEAL1.getDateTime(),MEAL1.getDescription(),MEAL1.getCalories());
        updated.setDescription("updatedDescription");
        updated.setCalories(555);
        updated.setDateTime(LocalDateTime.now());

        mockMvc.perform(put(REST_URL+MEAL1_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(mealService.get(MEAL1_ID,authUserId()),updated);
    }

//    assertMatch(service.getBetweenDates(
//                LocalDate.of(2015, Month.MAY, 30),
//                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    @Test
    void testGetBetween() throws Exception{
        mockMvc.perform(get(REST_URL+"/between?startDateTime=2015-05-30T07:00:00.000&endDateTime=2015-05-30T23:00:00.000"))//2000-10-31T01:30:00.000-05:00
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonWithExcess(MEAL3, MEAL2, MEAL1));
    }


}