package com.example.project2.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.project2.database.ProjectDataBase;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity (tableName = ProjectDataBase.USERPROFILE_TABLE)
public class UserProfile {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private int streak;
    private LocalDateTime date;
    private boolean secret;

    public UserProfile(String username) {
        this.id = id;
        this.username = username;
        this.streak = 1;
        this.date = LocalDateTime.now();
        this.secret = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isSecret() {
        return secret;
    }

    public void setSecret(boolean secret) {
        this.secret = secret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return id == that.id && streak == that.streak && secret == that.secret && Objects.equals(username, that.username) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, streak, date, secret);
    }
}