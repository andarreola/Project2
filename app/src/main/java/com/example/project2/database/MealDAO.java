package com.example.project2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2.database.entities.Meal;
import java.util.List;


@Dao
public interface MealDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Meal... meals);

    @Update
    void update(Meal... meals);

    @Delete
    void delete(Meal meal);

    @Query("SELECT * FROM " + MealDataBase.MEAL_TABLE + " ORDER BY date")
    List<Meal> getAllMeals();

    @Query("SELECT * FROM " + MealDataBase.MEAL_TABLE + " WHERE username == :username ORDER BY date DESC")
    LiveData<List<Meal>> getAllMealsByUsername(String username);

    @Query("DELETE from " + MealDataBase.MEAL_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + MealDataBase.MEAL_TABLE + " WHERE title == :title")
    LiveData<Meal> getMealByTitle(String title);

    @Query("UPDATE " + MealDataBase.MEAL_TABLE + " SET title = :nTitle WHERE title = :title")
    void updateTitle(String title, String nTitle);

}