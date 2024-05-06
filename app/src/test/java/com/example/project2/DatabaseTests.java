package com.example.project2;


import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.database.MealRepository;
import com.example.project2.database.UserProfileRepository;
import com.example.project2.database.UserRepository;
import com.example.project2.database.entities.Meal;
import com.example.project2.database.entities.User;
import com.example.project2.database.entities.UserProfile;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class DatabaseTests extends AppCompatActivity{

    UserRepository repository;
    MealRepository mealRepository;
    UserProfileRepository userProfileRepository;


    //User table tests


    @Test
    void userInsertTest() {
        User test = new User("username", "password");
        repository.insertUser(test);
        assertNotNull(repository.getUserByUserName("username"));
    }

    @Test
    public void userUpdateTest() {
        User test = new User("username", "password");
        repository.insertUser(test);
        repository.updatePassword("username", "password2");
        assertEquals(repository.getPasswordByUserName("username").getValue().toString(), "password2");
    }

    @Test
    public void userDeleteTest() {
        User test = new User("username", "password");
        repository.insertUser(test);
        repository.deleteUser(test);
        assertNull(repository.getUserByUserName("username"));
    }


    //Meal table tests


    @Test
    void mealInsertTest() {
        Meal test = new Meal("username", "meal", 1, 1);
        mealRepository.insertMeal(test);
        assertNotNull(mealRepository.getMealByTitle("meal"));
    }

    @Test
    void mealUpdateTest() {
        Meal test = new Meal("username", "title", 1, 1);
        mealRepository.insertMeal(test);
        mealRepository.updateTitle("title", "newT");
        assertNotNull(mealRepository.getMealByTitle("newT"));
    }

    @Test
    void mealDeleteTest() {
        Meal test = new Meal("username", "title", 1, 1);
        mealRepository.insertMeal(test);
        mealRepository.deleteMeal(test);
        assertNull(mealRepository.getMealByTitle("title"));
    }


    //UserProfile table tests here

}
