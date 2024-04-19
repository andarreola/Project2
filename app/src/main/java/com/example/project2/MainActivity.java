package com.example.project2;

import android.content.Intent;
import android.os.Bundle;

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
    private UserRepository repository;
    public static final String TAG = "DAC_CALORIE_TRACKER";
    int loggedInUserID = -1;
    //TODO: Add login information

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        loginUser();
        if(loggedInUserID == -1){
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }
        repository = UserRepository.getRepository(getApplication());
    }

    private void loginUser() {
        //TODO: create login method
    }
}