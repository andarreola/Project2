package com.example.project2.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.project2.database.entities.User;
import com.example.project2.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class UserRepository {
    private final UserDAO userDAO;
    private ArrayList<User> allUsers;

    private static  UserRepository repository;
    private UserRepository(Application application){
        ProjectDataBase db = ProjectDataBase.getDatabase(application);
        this.userDAO = db.userDAO();
        this.allUsers = (ArrayList<User>) this.userDAO.getAllUsers();
    }

    public static UserRepository getRepository(Application application){
        if(repository != null){
            return repository;
        }
        Future<UserRepository> future = ProjectDataBase.databaseWriteExecutor.submit(
                new Callable<UserRepository>() {
                    @Override
                    public UserRepository call() throws Exception {
                        return new UserRepository(application);
                    }
                }
        );
        try{
            return future.get();

        } catch (InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting UserRepository, thread error.");
        }
        return null;
    }
    public ArrayList<User> getAllUsers(){
        Future<ArrayList<User>> future = ProjectDataBase.databaseWriteExecutor.submit(
                new Callable<ArrayList<User>>() {
                    @Override
                    public ArrayList<User> call() throws Exception {
                        return (ArrayList<User>) userDAO.getAllUsers();
                    }
                });
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "Problem when getting all Users in the repository");
        }
        return null;
    }

    public void insertUser(User... user){
        ProjectDataBase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }


    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }
}
