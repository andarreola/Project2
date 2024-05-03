package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.databinding.ActivityViewEntriesBinding;


public class ViewEntriesActivity extends AppCompatActivity {

    private ActivityViewEntriesBinding binding;
    private static final String USERNAME_EXTRA_KEY = "LandingPage_Username";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewEntriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String username = getIntent().getStringExtra(USERNAME_EXTRA_KEY);

        binding.viewEntriesGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingPage.landingPageIntentFactory(getApplicationContext(), username);
                startActivity(intent);
            }
        });

    }

    static Intent viewEntriesIntentFactory(Context context, String username){
        Intent intent = new Intent(context, ViewEntriesActivity.class);
        intent.putExtra(USERNAME_EXTRA_KEY, username);
        return intent;
    }

    private void toastMaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}