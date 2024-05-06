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
import androidx.lifecycle.LiveData;

import android.widget.Toast;

import com.example.project2.database.MealRepository;
import com.example.project2.database.UserProfileRepository;
import com.example.project2.database.entities.Meal;
import com.example.project2.database.UserRepository;
import com.example.project2.database.entities.User;
import com.example.project2.databinding.ActivityLandingPageBinding;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class LandingPage extends AppCompatActivity {

    private static final String CONVERTED_VALUE_EXTRA_KEY = "LoginActivity_username";
    private MealRepository Mrepository;
    private UserRepository repository;
    private UserProfileRepository UPrepository;
    com.example.project2.databinding.ActivityLandingPageBinding binding;
    public final int calorieGoal = 2250;
    public final int proteinGoal = 65;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_landing_page);
        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Mrepository = MealRepository.getRepository(getApplication());
        repository = UserRepository.getRepository(getApplication());
        UPrepository = UserProfileRepository.getRepository(getApplication());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String username = getIntent().getStringExtra(CONVERTED_VALUE_EXTRA_KEY);
        binding.displayusername.setText(username);


        //Progress bar build below
        createGoalProtein(proteinGoal);
        createGoalCalories(calorieGoal);
        ArrayList<Meal> meals = Mrepository.getAllMeals();
        ArrayList<Meal> userMeals = new ArrayList<>();
        for(int i = 0; i < meals.size(); i++) {
            if(meals.get(i).getUsername().equals(username)) {
                userMeals.add(meals.get(i));
            }
        }
        updateProteinCount(userMeals);
        updateCalorieCount(userMeals);
        //done


        int streak = UPrepository.getUserProfileByUsername(username).getStreak();
        int lastLoginDate = UPrepository.getUserProfileByUsername(username).getDate().getDayOfYear();

        int todayDate = LocalDateTime.now().getDayOfYear();
        if(lastLoginDate == todayDate) {
            //nothing
        }
        else if(lastLoginDate +1 == todayDate) {
            streak++;
            UPrepository.updateStreak(username, streak);
            UPrepository.updateDate(username, LocalDateTime.now());
        }
        else {
            streak = 1;
            UPrepository.updateStreak(username, streak);
            UPrepository.updateDate(username, LocalDateTime.now());
        }

        StringBuilder sb = new StringBuilder();
        sb.append(streak);
        String text = sb.toString();
        binding.displaystreak.setText(text);


        if(UPrepository.getUserProfileByUsername(username).isSecret()) {
            binding.secrettext.setText("You found the secret!!");
        }
        else {
            binding.secrettext.setText("Secret has not been found.");
        }


        LiveData<User> userObserver = repository.getIsAdminByUserName(username);
        userObserver.observe(this, user -> {
            if(user != null) {
                System.out.println(user.getIsAdmin());
                if (user.getIsAdmin()) {
                    binding.adminButton.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.addlogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MealLogActivity.landingPageToMealLogIntent(getApplicationContext(), username);
                startActivity(intent);
            }
        });

        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LogViewActivity.landingPageToViewLogIntent(getApplicationContext(), username);
                startActivity(intent);
            }
        });

        //watching log out button for prompt to log out and send intent
        binding.logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.mainActivityFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        //For button to go into the adminPage
        binding.adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminPage.adminPageIntentFactory(getApplicationContext(), username);
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

    public void updateProteinCount(ArrayList<Meal> meals) {
        int addition = 0;
        for(int i = 0; i < meals.size(); i++) {
            int today = (LocalDate.now().getDayOfYear())*(LocalDate.now().getYear());
            if((meals.get(i).getDate().getDayOfYear()) * (meals.get(i).getDate().getYear()) == today) {
                addition += meals.get(i).getProtein();
            }
        }
        binding.proteintracker.setProgress(binding.proteintracker.getProgress()+addition);
    }

    public void updateCalorieCount(ArrayList<Meal> meals) {
        int addition = 0;
        for(int i = 0; i < meals.size(); i++) {
            int today = (LocalDate.now().getDayOfYear())*(LocalDate.now().getYear());
            if((meals.get(i).getDate().getDayOfYear()) * (meals.get(i).getDate().getYear()) == today) {
                addition += meals.get(i).getCalories();
            }
        }
        binding.calorietracker.setProgress(binding.calorietracker.getProgress()+addition);
    }

    static Intent landingPageIntentFactory(Context context, String username){
        Intent intent = new Intent(context, LandingPage.class);
        intent.putExtra(CONVERTED_VALUE_EXTRA_KEY, username);
        return intent;
    }

    static Intent mealLogToLandingPageIntent(Context context, String username) {
        Intent intent = new Intent(context, LandingPage.class);
        intent.putExtra(CONVERTED_VALUE_EXTRA_KEY, username);
        return intent;
    }

    static Intent viewLogToLandingPageIntent(Context context, String username) {
        Intent intent = new Intent(context, LandingPage.class);
        intent.putExtra(CONVERTED_VALUE_EXTRA_KEY, username);
        return intent;
    }

    private void toastMaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}