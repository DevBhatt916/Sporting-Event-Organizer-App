package com.example.a2_2081_dev_bhatt.DatabaseProvider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.a2_2081_dev_bhatt.Category;
import com.example.a2_2081_dev_bhatt.Event;

import java.util.List;

public class EventCategoryViewModel extends AndroidViewModel {

    // reference to CardRepository
    private EventCategoryRepository repository;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<Event>> allEventsLiveData;
    private LiveData<List<Category>> allCategoriesLiveData;


    public EventCategoryViewModel(@NonNull Application application) {
        super(application);


        // get reference to the repository class
        repository = new EventCategoryRepository(application);

        // get all items by calling method defined in repository class
        allEventsLiveData = repository.getAllEvents();
        allCategoriesLiveData = repository.getAllCategories();
    }

    /**
     * ViewModel method to get all cards
     * @return LiveData of type List<Item>
     */
    public LiveData<List<Event>> getAllEvents() {
        return allEventsLiveData;
    }
    public LiveData<List<Category>> getAllCategories() {
        return allCategoriesLiveData;
    }


    public void insert(Event event) {
        repository.insert(event);
    }

    public void insert(Category category) {
        repository.insert(category);
    }

    public void deleteEvent(Event event) {
        repository.deleteEvent(event);
    }

    public void deleteCategory(Category category) {
        repository.deleteCategory(category);
    }

    public void deleteAllCategories() {
        repository.deleteAllCategories();
    }

    public void deleteAllEvents() {
        repository.deleteAllEvents();
    }

    public void incrementEventCount(String categoryId) {
        repository.incrementEventCount(categoryId);
    }

    public void updateCategory(Category category){
        repository.updateCategory(category);
    }
}
