package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;

import com.example.project2.database.UserRepository;
import com.example.project2.database.entities.User;
import com.example.project2.databinding.ActivitySignUpPageBinding;

public class SignUpPage extends AppCompatActivity {

    private ActivitySignUpPageBinding binding;
    private UserRepository repository;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserRepository.getRepository(getApplication());

        binding.signUpGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.mainActivityFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignUp();
            }
        });

    }

    static Intent signUpPageFactory(Context context){
        Intent intent = new Intent(context, SignUpPage.class);
        return intent;
    }
    //Sign up code below: Created 2:40pm
    private void userSignUp(){
        String username = binding.signUpUsernameEditText.getText().toString();
        String password = binding.signUpPasswordEditText.getText().toString();
        if(username.isEmpty() || password.isEmpty()){
            toastMaker("Fields can not be empty.");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if(user != null){
                //this checks if the user already exists in the database. If they do, it will make a toast letting the user know that the user already exists.
                toastMaker(username + " is already taken");
            } else {
                //if the user does not exists, a new user will be added to the database.
                repository.insertUser(new User(username, password));
                toastMaker(username);
                toastMaker(password);
            }
        });

    }

    private void toastMaker(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}