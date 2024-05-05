package com.example.project2.ViewHolders;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.project2.database.UserRepository;
import com.example.project2.database.entities.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository repository;

    public UserViewModel (Application application){
        super(application);
        repository = UserRepository.getRepository(application);
    }

    //Part may be buggy while implementing
    public LiveData<List<User>> getAllUsers(){
        return repository.getAllLiveUsers();
    }

}
