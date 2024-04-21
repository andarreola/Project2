package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.room.*;

import com.example.project2.database.UserRepository;
import com.example.project2.database.entities.User;

import java.util.*;

public class LandingPage extends AppCompatActivity {

    private UserRepository repository;
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

        repository = UserRepository.getRepository(getApplication());

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

    static Intent landingPageIntentFactory(Context context){
        return new Intent(context, LandingPage.class);
    }

    private void toastMaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



}