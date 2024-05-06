package com.example.project2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2.database.entities.User;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("UPDATE " + ProjectDataBase.USER_TABLE + " SET password = :password WHERE username = :username")
    void updatePassword(String username, String password);

    @Query("SELECT * FROM " + ProjectDataBase.USER_TABLE + " ORDER BY username")
    List<User> getAllUsers();

    @Query("DELETE from " + ProjectDataBase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * from " + ProjectDataBase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getUserByUserName(String username);

    @Query("SELECT * from " + ProjectDataBase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getPasswordByUserName(String username);

    @Query("SELECT * from " + ProjectDataBase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getIsAdminByUserName(String username);

    @Query("SELECT * from " + ProjectDataBase.USER_TABLE + " WHERE id == :id")
    LiveData<User> getUserById(String id);

    //for recycler
    @Query("SELECT * FROM " + ProjectDataBase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllLiveUsers();
}
