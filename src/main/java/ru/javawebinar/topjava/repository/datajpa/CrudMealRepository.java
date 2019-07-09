package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    // null if updated meal do not belong to userId
//    @Transactional
//    @Modifying
//    Meal save(Meal meal, int userId);

    // false if meal do not belong to userId
    @Transactional
    @Modifying
    @Query(name = Meal.DELETE)
    int delete(@Param("id") int mealId, @Param("userId") int userId);

    // null if meal do not belong to userId
//    @Query("select m from Meal m where m.id=:id and m.user.id=:userId")
//    Meal get(int id, int userId);

    // ORDERED dateTime desc
//    List<Meal> getAll(int userId);

    // ORDERED dateTime desc
//    (name = Meal.GET_BETWEEN, query = "SELECT m FROM Meal m " +
//            "WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime DESC")
    @Query(name = Meal.GET_BETWEEN)
    List<Meal> getBetween(@Param("startDate")LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);
}
