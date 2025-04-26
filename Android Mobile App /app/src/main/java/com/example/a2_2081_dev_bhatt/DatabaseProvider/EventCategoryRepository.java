package com.example.a2_2081_dev_bhatt.DatabaseProvider;


import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;

import com.example.a2_2081_dev_bhatt.Category;
import com.example.a2_2081_dev_bhatt.Event;

import java.util.List;

public class EventCategoryRepository {

    // private class variable to hold reference to DAO
    private EventCategoryDAO DAO;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private final LiveData<List<Event>> allEventsLiveData;
    private final LiveData<List<Category>> allCategoriesLiveData;

    // constructor to initialise the repository class
    EventCategoryRepository(Application application) {
        // get reference/instance of the database
        EventCategoryDatabase db = EventCategoryDatabase.getDatabase(application);

        // get reference to DAO, to perform CRUD operations
        DAO = db.DAO();

        // once the class is initialised get all the items in the form of LiveData
        allEventsLiveData = DAO.getAllEvents();
        allCategoriesLiveData = DAO.getAllCategories();
    }

    /**
     * Repository method to get all cards
     * @return LiveData of type List<Item>
     */
    LiveData<List<Event>> getAllEvents() {
        return allEventsLiveData;
    }

    LiveData<List<Category>> getAllCategories() {
        return allCategoriesLiveData;
    }


    void insert(Event event) {
        // Executes the database operation to insert the item in a background thread.
        EventCategoryDatabase.databaseWriteExecutor.execute(() -> DAO.addEvent(event));
    }

    void insert(Category category) {
        // Executes the database operation to insert the item in a background thread.
        EventCategoryDatabase.databaseWriteExecutor.execute(() -> DAO.addCategory(category));
    }

    void deleteEvent(Event event) {
        // Executes the database operation to delete the item in a background thread.
        EventCategoryDatabase.databaseWriteExecutor.execute(() -> DAO.deleteEvent(event));
    }

    void deleteCategory(Category category) {
        // Executes the database operation to delete the item in a background thread.
        EventCategoryDatabase.databaseWriteExecutor.execute(() -> DAO.deleteCategory(category));
    }

    void update(Event event) {
        // Executes the database operation to update the item in a background thread.
        EventCategoryDatabase.databaseWriteExecutor.execute(() -> DAO.updateEvent(event));
    }

    void updateCategory(Category category) {
        // Executes the database operation to update the item in a background thread.
        EventCategoryDatabase.databaseWriteExecutor.execute(() -> DAO.updateCategory(category));
    }

    void deleteAllCategories() {
        EventCategoryDatabase.databaseWriteExecutor.execute(() -> DAO.deleteAllCategories());
    }

    void deleteAllEvents() {
        EventCategoryDatabase.databaseWriteExecutor.execute(() -> DAO.deleteAllEvents());
    }

    public void incrementEventCount(String categoryId) {
        EventCategoryDatabase.databaseWriteExecutor.execute(() -> DAO.incrementEventCount(categoryId));
    }



}
