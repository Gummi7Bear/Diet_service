package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        //System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    /*Должны возвращаться только записи между `startTime` и `endTime`
      Поле `UserMealWithExcess.excess` должно показывать, превышает ли сумма калорий за весь день значение `caloriesPerDay` */
    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        //Узнать общую сумму калорий за день
        Map<LocalDate, Integer> groupMealsByDate = new HashMap<>();
        for (UserMeal meal : meals) {
            if (groupMealsByDate.containsKey(meal.getDate())) {
                int calories = groupMealsByDate.get(meal.getDate());
                groupMealsByDate.put(meal.getDate(), calories + meal.getCalories());
            } else {
                groupMealsByDate.put(meal.getDate(), meal.getCalories());
            }
        }
        //Сортировка обедов по дате и времени и создание списка обедов с учетом нормы калорий за день
        List<UserMealWithExcess> listMeals = new ArrayList<>();
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
            {
                UserMealWithExcess mealWithExcess = new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        groupMealsByDate.get(meal.getDate()) > caloriesPerDay ? true : false
                );
                listMeals.add(mealWithExcess);
            }
        }
        return listMeals;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
