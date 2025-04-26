package com.example.a2_2081_dev_bhatt.DatabaseProvider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.a2_2081_dev_bhatt.Category;
import com.example.a2_2081_dev_bhatt.Event;

import java.util.List;

@Dao
public interface EventCategoryDAO {
    @Query("select * from events")
    LiveData<List<Event>> getAllEvents();
    @Query("select * from category")
    LiveData<List<Category>> getAllCategories();

    @Insert()
    void addEvent(Event event);


    @Insert()
    void addCategory(Category category);

    @Delete()
    void deleteEvent(Event event);
    @Delete()
    void deleteCategory(Category category);

    @Update()
    void updateEvent(Event event);
    @Update()
    void updateCategory(Category category);

    @Query("delete from category")
    void deleteAllCategories();

    @Query("delete from events")
    void deleteAllEvents();

    @Query("UPDATE category SET EventCount = EventCount + 1 WHERE CategoryID = :categoryId")
    void incrementEventCount(String categoryId);
}
