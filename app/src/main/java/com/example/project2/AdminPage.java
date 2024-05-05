package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.project2.ViewHolders.UserAdapter;
import com.example.project2.ViewHolders.UserViewModel;
import com.example.project2.database.UserRepository;
import com.example.project2.database.entities.User;
import com.example.project2.databinding.ActivityAdminPageBinding;

public class AdminPage extends AppCompatActivity {

    private static final String USERNAME_FROM_LANDING_KEY = "LandingPage_Activity_Username";
    private UserRepository repository;
    private User user = null;
    private ActivityAdminPageBinding binding;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserRepository.getRepository(getApplication());

        String username = getIntent().getStringExtra(USERNAME_FROM_LANDING_KEY);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        RecyclerView recyclerView = binding.userDisplayRecyclerView;
        final UserAdapter adapter = new UserAdapter(new UserAdapter.UserDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userViewModel.getAllUsers().observe(this, users -> {
            adapter.submitList(users);
        });

        binding.adminPageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingPage.landingPageIntentFactory(getApplicationContext(), username);
                startActivity(intent);
            }
        });

    }

    static Intent adminPageIntentFactory(Context context, String username){
        Intent intent = new Intent(context, AdminPage.class);
        intent.putExtra(USERNAME_FROM_LANDING_KEY, username);
        return intent;
    }




}