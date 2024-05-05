package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.project2.R;
import com.example.project2.database.MealDataBase;
import com.example.project2.database.MealRepository;
import com.example.project2.database.entities.Meal;
import com.example.project2.database.UserRepository;
import com.example.project2.database.entities.User;
import com.example.project2.databinding.ActivityLandingPageBinding;
import com.example.project2.databinding.ActivityMealLogBinding;

public class MealLogActivity extends AppCompatActivity {

    private ActivityMealLogBinding binding;
    private MealRepository repository;
    private Meal meal = null;
    private static final String CONVERTED_VALUE_EXTRA_KEY = "LoginActivity_username";
    private String username1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_meal_log);
        binding = ActivityMealLogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        repository = MealRepository.getRepository(getApplication());
        //repository = new MealRepository(getApplication());

        String username = getIntent().getStringExtra(CONVERTED_VALUE_EXTRA_KEY);
        this.username1 = username;

        //idk what this is
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        //submits log info to database
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMaker("You clicked the button!");
                addMealLog();
            }
        });

        //Intent for back button
        binding.backHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingPage.mealLogToLandingPageIntent(getApplicationContext(), username);
                startActivity(intent);
            }
        });
    }

    public void addMealLog() {
        String title = binding.mealtitleEdittext.getText().toString();
        Integer protein = Integer.parseInt(binding.proteinEdittext.getText().toString());
        Integer calories = Integer.parseInt(binding.calorieEdittext.getText().toString());
        if(title.isEmpty() || protein == null || calories == null){
            toastMaker("Please fill all text boxes.");
            return;
        }
        repository.insertMeal(new Meal(username1, title, protein, calories));
        toastMaker("Entry added successfully!");
    }

    private void toastMaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent landingPageToMealLogIntent(Context context, String username) {
        Intent intent = new Intent(context, MealLogActivity.class);
        intent.putExtra(CONVERTED_VALUE_EXTRA_KEY, username);
        return intent;
    }


}