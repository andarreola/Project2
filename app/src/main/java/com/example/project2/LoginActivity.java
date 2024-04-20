package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project2.database.UserRepository;
import com.example.project2.database.entities.User;
import com.example.project2.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private UserRepository repository;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verifyUser()){
                    toastMaker("Invalid username or password");
                } else {
                    //Run landing page once that is implemented.
                }
            }
        });
    }

    private boolean verifyUser(){
        String username = binding.userNameLoginEditText.getText().toString();
        if(username.isEmpty()){
            toastMaker("Username may not be blank");
            return false;
        }
        user = repository.getUserByUserName(username);
        if(user != null){
            String password = binding.passwordLoginEditText.getText().toString();
            if(password.equals(user.getPassword())){
                return true;
            } else {
                toastMaker("Username may not be blank");
                return false;
            }

        }
        toastMaker(String.format("No %s found", username));
        return false;
    }



    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }

    private void toastMaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}