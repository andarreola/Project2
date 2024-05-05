package com.example.project2.database;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project2.database.entities.Meal;
import com.example.project2.database.entities.User;
import com.example.project2.MainActivity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Meal.class}, version = 1, exportSchema = false)
public abstract class MealDataBase extends RoomDatabase {

    private static final String DATABASE_NAME = "Project_database";
    public static final String MEAL_TABLE = "mealTable";

    private static volatile MealDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static MealDataBase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (MealDataBase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    MealDataBase.class,
                                    DATABASE_NAME
                            )
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
                MealDAO dao = INSTANCE.mealDAO();
                dao.deleteAll();
                //put entries here if you want
            });
        }
    };

    public abstract MealDAO mealDAO();
}
