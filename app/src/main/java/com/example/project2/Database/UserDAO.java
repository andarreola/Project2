package com.example.project2.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2.Database.entities.User;

import java.util.ArrayList;


@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("Select * from " + UserDatabase.USER_TABLE)
    ArrayList<User> getAllRecords();
}
