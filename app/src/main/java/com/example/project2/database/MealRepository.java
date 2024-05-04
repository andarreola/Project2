package com.example.project2.database;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;

import com.example.project2.database.entities.Meal;
import com.example.project2.database.entities.User;
import com.example.project2.MainActivity;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MealRepository {
    private final MealDAO mealDAO;
    private ArrayList<Meal> allMeals;

    private static MealRepository repository;
    private MealRepository(Application application){
        MealDataBase db = MealDataBase.getDatabase(application);
        this.mealDAO = db.mealDAO();
        this.allMeals = (ArrayList<Meal>) this.mealDAO.getAllMeals();
    }

    public static MealRepository getRepository(Application application){
        if(repository != null){
            return repository;
        }
        Future<MealRepository> future = MealDataBase.databaseWriteExecutor.submit(
                new Callable<MealRepository>() {
                    @Override
                    public MealRepository call() throws Exception {
                        return new MealRepository(application);
                    }
                }
        );
        try{
            return future.get();

        } catch (InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting MealRepository, thread error.");
        }
        return null;
    }
    public ArrayList<Meal> getAllMeals(){
        Future<ArrayList<Meal>> future = MealDataBase.databaseWriteExecutor.submit(
                new Callable<ArrayList<Meal>>() {
                    @Override
                    public ArrayList<Meal> call() throws Exception {
                        return (ArrayList<Meal>) mealDAO.getAllMeals();
                    }
                });
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "Problem when getting all Meals in the repository");
        }
        return null;
    }

    public void insertMeal(Meal... meal){
        MealDataBase.databaseWriteExecutor.execute(() ->
        {
            mealDAO.insert(meal);
        });
    }


    public LiveData<Meal> getMealByTitle(String title) {
        return mealDAO.getMealByTitle(title);
    }

}
