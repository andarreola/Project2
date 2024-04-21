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

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.example.project2.database.UserRepository;
import com.example.project2.database.entities.User;
import com.example.project2.databinding.ActivityLandingPageBinding;

import java.util.*;

public class LandingPage extends AppCompatActivity {

    private static final String CONVERTED_VALUE_EXTRA_KEY = "LoginActivity_username";
    private UserRepository repository;
    com.example.project2.databinding.ActivityLandingPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_landing_page);
        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserRepository.getRepository(getApplication());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String username = getIntent().getStringExtra(CONVERTED_VALUE_EXTRA_KEY);

        System.out.println(username);
        LiveData<User> userObserver = repository.getIsAdminByUserName(username);
        userObserver.observe(this, user -> {
            if(user != null) {
                System.out.println(user.getIsAdmin());
                if (user.getIsAdmin()) {
                    binding.adminButton.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.mainActivityFactory(getApplicationContext());
                startActivity(intent);
            }
        });
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

    static Intent landingPageIntentFactory(Context context, String username){
        Intent intent = new Intent(context, LandingPage.class);
        intent.putExtra(CONVERTED_VALUE_EXTRA_KEY, username);
        return intent;
    }

    private void toastMaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}