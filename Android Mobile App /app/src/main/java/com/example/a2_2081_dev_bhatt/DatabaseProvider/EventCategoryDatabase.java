package com.example.a2_2081_dev_bhatt.DatabaseProvider;


import android.app.usage.UsageEvents;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.a2_2081_dev_bhatt.Category;
import com.example.a2_2081_dev_bhatt.Event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




@Database(entities = {Event.class, Category.class}, version = 1)
public abstract class EventCategoryDatabase extends RoomDatabase {

    public static final String EVENTCATEGORY_DATABASE = "eventCategory_database";

    public abstract EventCategoryDAO DAO();
    private static volatile EventCategoryDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Since this class is an absract class, to get the database reference we would need
     * to implement a way to get reference to the database.
     *
     * @param context Application of Activity Context
     * @return a reference to the database for read and write operation
     */

    static EventCategoryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EventCategoryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    EventCategoryDatabase.class, EVENTCATEGORY_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}


















