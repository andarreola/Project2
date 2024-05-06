package com.example.project2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.project2.database.entities.UserProfile;

import java.time.LocalDateTime;
import java.util.List;

@Dao
public interface UserProfileDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserProfile... userProfiles);

    @Update
    void update(UserProfile... userProfiles);

    @Delete
    void delete(UserProfile userProfile);

    @Query("SELECT * FROM " + ProjectDataBase.USERPROFILE_TABLE + " ORDER BY username")
    List<UserProfile> getAllUserProfiles();

    @Query("DELETE from " + ProjectDataBase.USERPROFILE_TABLE)
    void deleteAll();

    @Query("SELECT * from " + ProjectDataBase.USERPROFILE_TABLE + " WHERE username == :username")
    UserProfile getUserProfileByUserName(String username);

    @Query("SELECT * from " + ProjectDataBase.USERPROFILE_TABLE + " WHERE username == :username")
    boolean getSecretByUserName(String username);

    @Query("SELECT * from " + ProjectDataBase.USERPROFILE_TABLE + " WHERE username == :username")
    int getStreakByUserName(String username);

    @Query("SELECT * from " + ProjectDataBase.USERPROFILE_TABLE + " WHERE username == :username")
    int getDateByUserName(String username);

    @Query("UPDATE " + ProjectDataBase.USERPROFILE_TABLE + " SET streak = :value WHERE username = :username")
    void updateStreak(String username, int value);

    @Query("UPDATE " + ProjectDataBase.USERPROFILE_TABLE + " SET date = :data WHERE username = :username")
    void updateDate(String username, LocalDateTime data);

    @Query("UPDATE " + ProjectDataBase.USERPROFILE_TABLE + " SET secret = :update WHERE username = :username")
    void updateSecret(String username, boolean update);
}
