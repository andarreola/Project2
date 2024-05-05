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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.ViewHolders.MealLogAdapter;
import com.example.project2.ViewHolders.MealLogViewModel;
import com.example.project2.database.MealRepository;
import com.example.project2.databinding.ActivityLogViewBinding;

public class LogViewActivity extends AppCompatActivity {

    private ActivityLogViewBinding binding;
    private MealRepository repository;
    private static final String CONVERTED_VALUE_EXTRA_KEY = "LoginActivity_username";

    private MealLogViewModel mealLogViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_log_view);
        binding = com.example.project2.databinding.ActivityLogViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = MealRepository.getRepository(getApplication());

        String username = getIntent().getStringExtra(CONVERTED_VALUE_EXTRA_KEY);


        mealLogViewModel = new ViewModelProvider(this).get(MealLogViewModel.class);

        RecyclerView recyclerView = binding.logDisplayRecyclerView;
        final MealLogAdapter adapter = new MealLogAdapter(new MealLogAdapter.MealLogDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mealLogViewModel.getAllLogsByUsername(username).observe(this, meals -> {
                    adapter.submitList(meals);
                });

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        binding.fromLogtoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingPage.viewLogToLandingPageIntent(getApplicationContext(), username);
                startActivity(intent);
            }
        });

    }

    static Intent landingPageToViewLogIntent(Context context, String username) {
        Intent intent = new Intent(context, LogViewActivity.class);
        intent.putExtra(CONVERTED_VALUE_EXTRA_KEY, username);
        return intent;
    }
}