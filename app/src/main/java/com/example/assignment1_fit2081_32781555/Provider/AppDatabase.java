package com.example.assignment1_fit2081_32781555.Provider;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.assignment1_fit2081_32781555.Objects.Category;
import com.example.assignment1_fit2081_32781555.Objects.Event;
import com.example.assignment1_fit2081_32781555.Provider.DAO.CategoryDAO;
import com.example.assignment1_fit2081_32781555.Provider.DAO.EventDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Event.class, Category.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    public abstract CategoryDAO categoryDAO();
    public abstract EventDAO eventDAO();

    public static final String APP_DATABASE = "app_database";
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, APP_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
