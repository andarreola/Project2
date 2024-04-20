package com.example.project2;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ProgressBar;

import androidx.room.*;
//import androidx.room.ColumnInfo;
//import androidx.room.Dao;
//import androidx.room.Database;
//import androidx.room.Delete;
//import androidx.room.Entity;
//import androidx.room.Insert;
//import androidx.room.PrimaryKey;
import java.util.*;

public class LandingPage extends AppCompatActivity {

    com.example.project2.databinding.ActivityLandingPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_landing_page);
        binding = com.example.project2.databinding.ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "Admin-UserTable").build();

    }

    public void createGoalProtein(int goal) {
        binding.proteintracker.setMax(goal);
        binding.proteintracker.setProgress(0);
    }

    public void createGoalCalories(int goal) {
        binding.calorietracker.setMax(goal);
        binding.calorietracker.setProgress(0);
    }

    public void updateProteinCount(int addition) {
        binding.proteintracker.setProgress(binding.proteintracker.getProgress()+addition);
    }

    public void updateCalorieCount(int addition) {
        binding.calorietracker.setProgress(binding.calorietracker.getProgress()+addition);
    }

    //Admin Database

    @Entity
    public class User {
        @PrimaryKey
        public int uid;

        @ColumnInfo(name = "username")
        public String username;

        @ColumnInfo(name = "isAdmin")
        public boolean isAdmin;
    }

    @Dao
    public interface UserDao {
        @Query("SELECT * FROM user")
        List<User> getAll();

//        @Query("SELECT * FROM user WHERE username LIKE :username)
//        User findByUsername(String username);

        @Insert
        void insertAll(User... users);

        @Delete
        void delete(User user);
    }

    @Database(entities = {User.class}, version = 1)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract UserDao userDao();
    }

}