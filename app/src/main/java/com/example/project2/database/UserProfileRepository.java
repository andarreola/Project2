package com.example.project2.database;

import android.app.Application;
import android.util.Log;

import com.example.project2.MainActivity;
import com.example.project2.database.entities.UserProfile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class UserProfileRepository {
    private final UserProfileDAO userProfileDAO;
    private ArrayList<UserProfile> allUserProfiles;
    private static  UserProfileRepository repository;

    private UserProfileRepository(Application application){
        ProjectDataBase db = ProjectDataBase.getDatabase(application);
        this.userProfileDAO = db.userProfileDAO();
        this.allUserProfiles = (ArrayList<UserProfile>) this.userProfileDAO.getAllUserProfiles();
    }

    public static UserProfileRepository getRepository(Application application){
        if(repository != null){
            return repository;
        }
        Future<UserProfileRepository> future = ProjectDataBase.databaseWriteExecutor.submit(
                new Callable<UserProfileRepository>() {
                    @Override
                    public UserProfileRepository call() throws Exception {
                        return new UserProfileRepository(application);
                    }
                }
        );
        try{
            return future.get();

        } catch (InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting UserProfileRepository, thread error.");
        }
        return null;
    }
    public ArrayList<UserProfile> getAllUserProfiles(){
        Future<ArrayList<UserProfile>> future = ProjectDataBase.databaseWriteExecutor.submit(
                new Callable<ArrayList<UserProfile>>() {
                    @Override
                    public ArrayList<UserProfile> call() throws Exception {
                        return (ArrayList<UserProfile>) userProfileDAO.getAllUserProfiles();
                    }
                });
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "Problem when getting all Users in the repository");
        }
        return null;
    }

    public void insertUser(UserProfile... userProfile){
        ProjectDataBase.databaseWriteExecutor.execute(() ->
        {
            userProfileDAO.insert(userProfile);
        });
    }

    public void deleteUser(UserProfile userProfile) {
        ProjectDataBase.databaseWriteExecutor.execute(() ->
        {
            userProfileDAO.delete(userProfile);
        });
    }

    public void updateStreak(String username, int value) {
        userProfileDAO.updateStreak(username, value);
    }

    public void updateDate(String username, LocalDateTime date) {
        userProfileDAO.updateDate(username, date);
    }

    public void updateSecret(String username, boolean update) {
        userProfileDAO.updateSecret(username, update);
    }

    public UserProfile getUserProfileByUsername(String username) {
        return userProfileDAO.getUserProfileByUserName(username);
    }

    public boolean getSecretByUserName(String username) {
        return userProfileDAO.getSecretByUserName(username);
    }

    public int getStreakByUserName(String username) {
        return userProfileDAO.getStreakByUserName(username);
    }

    public int getDateByUserName(String username) {
        return userProfileDAO.getDateByUserName(username);
    }

}