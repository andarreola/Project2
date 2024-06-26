package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import com.example.project2.database.MealRepository;
import com.example.project2.database.UserProfileRepository;
import com.example.project2.database.entities.Meal;
import com.example.project2.database.UserRepository;
import com.example.project2.database.entities.User;
import com.example.project2.database.entities.UserProfile;
import com.example.project2.databinding.ActivityLoginBinding;

import java.time.LocalDateTime;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private MealRepository Mrepository;
    private UserRepository repository;
    private UserProfileRepository UPrepository;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Mrepository = MealRepository.getRepository(getApplication());
        repository = UserRepository.getRepository(getApplication());
        UPrepository = UserProfileRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });

        binding.returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.mainActivityFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void verifyUser(){
        String username = binding.userNameLoginEditText.getText().toString();

        if(username.isEmpty()){
            toastMaker("Username may not be blank");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if(user != null){
                String password = binding.passwordLoginEditText.getText().toString();
                if(password.equals(user.getPassword())){
                    Intent intent = LandingPage.landingPageIntentFactory(getApplicationContext(), username);
                    startActivity(intent);
                    toastMaker("SUCCESS!!!");
                } else {
                    toastMaker("Invalid password");
                    binding.passwordLoginEditText.setSelection(0);
                }
            } else {
                toastMaker(String.format("No user %s found", username));
                binding.userNameLoginEditText.setSelection(0);
            }
        });
    }



    static Intent loginIntentFactory(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }


    private void toastMaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}