package com.example.project2.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project2.database.TypeConverters.LocalDateTypeConverter;
import com.example.project2.database.entities.User;
import com.example.project2.MainActivity;
import com.example.project2.database.entities.UserProfile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {User.class, UserProfile.class}, version = 2, exportSchema = false)
public abstract class ProjectDataBase extends RoomDatabase {

    private static final String DATABASE_NAME = "Project_database";
    public static final String USER_TABLE = "userTable";
    public static final String USERPROFILE_TABLE = "userProfileTable";

    private static volatile ProjectDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static ProjectDataBase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (ProjectDataBase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    ProjectDataBase.class,
                                    DATABASE_NAME
                            )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                UserProfileDAO dao2 = INSTANCE.userProfileDAO();

                dao.deleteAll();
                dao2.deleteAll();

                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);
                UserProfile admin2 = new UserProfile("admin1");
                dao2.insert(admin2);

                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);
                UserProfile testUser12 = new UserProfile("testuser1");
                dao2.insert(testUser12);

            });
        }
    };

    public abstract UserDAO userDAO();
    public abstract UserProfileDAO userProfileDAO();
}
