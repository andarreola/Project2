package com.example.project2.ViewHolders;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.project2.database.MealRepository;
import com.example.project2.database.entities.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealLogViewModel extends AndroidViewModel {
    private final MealRepository repository;
    //private final LiveData<List<Meal>> allLogsByUsername;
    public MealLogViewModel (Application application) {
        super(application);
        repository = MealRepository.getRepository(application);
        //allLogsByUsername = repository.getAllMealsByUsername(username);

    }

    public LiveData<List<Meal>> getAllLogsByUsername(String username) {
        return repository.getAllMealsByUsername(username);
    }

    public void insert(Meal meal) {
        repository.insertMeal(meal);
    }
}
