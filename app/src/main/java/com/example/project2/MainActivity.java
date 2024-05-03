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

import com.example.project2.database.ProjectDataBase;
import com.example.project2.database.UserRepository;
import com.example.project2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    //private UserRepository repository;
    public static final String TAG = "DAC_CALORIE_TRACKER";
    int loggedInUserID = -1;
    //TODO: Add login information

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        loginUser();
        //This is used to check if the user is logged in.
        if(loggedInUserID != -1){
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        binding.mainActivityLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.mainCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignUpPage.signUpPageFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        //repository = UserRepository.getRepository(getApplication());
    }

    private void loginUser() {
        //TODO: create login method
    }

    static Intent mainActivityFactory(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}