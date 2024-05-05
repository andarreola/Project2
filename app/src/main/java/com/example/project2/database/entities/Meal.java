package com.example.project2.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.project2.database.MealDataBase;
import java.util.Objects;

@Entity (tableName = MealDataBase.MEAL_TABLE)
public class Meal {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String username;
    private int protein;
    private int calories;

    public Meal(String username, String title, int protein, int calories){
        this.username = username;
        this.title = title;
        this.protein = protein;
        this.calories = calories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return username.equals(meal.username) && id == meal.id && Objects.equals(title, meal.title) && protein == meal.protein && calories == meal.calories;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, title, protein, calories);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCalories() {
        return calories;
    }

    public void setAdmin(int calories) {
        this.calories = calories;
    }
}
