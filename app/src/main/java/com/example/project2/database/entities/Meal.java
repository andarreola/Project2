package com.example.project2.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.project2.database.MealDataBase;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity (tableName = MealDataBase.MEAL_TABLE)
public class Meal {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String username;
    private int protein;
    private int calories;
    private LocalDateTime date;

    public Meal(String username, String title, int protein, int calories){
        this.username = username;
        this.title = title;
        this.protein = protein;
        this.calories = calories;
        date = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return username.equals(meal.username) && id == meal.id && Objects.equals(title, meal.title) && protein == meal.protein && calories == meal.calories && date == meal.date;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, title, protein, calories, date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getDate() {
        return date;
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append("\n");
        sb.append("Protein: ").append(protein).append("\n");
        sb.append("Calories: ").append(calories).append("\n");
        sb.append("Date: ").append(date.toString()).append("\n");
        sb.append("- - - - - - - - - - - - - - - - - - - - - - - - -");
        return sb.toString();
    }
}
