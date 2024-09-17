package com.example.assignment1_fit2081_32781555.Provider;


import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.example.assignment1_fit2081_32781555.Objects.Category;
import com.example.assignment1_fit2081_32781555.Objects.Event;
import com.example.assignment1_fit2081_32781555.Provider.DAO.CategoryDAO;
import com.example.assignment1_fit2081_32781555.Provider.DAO.EventDAO;
import com.example.assignment1_fit2081_32781555.Utility.EventAddCallback;

import java.util.List;

public class Repository {

    private Handler mainHandler = new Handler(Looper.getMainLooper());

    // private class variable to hold reference to DAO
    private EventDAO eventDAO;
    private CategoryDAO categoryDAO;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<Event>> allEventsLiveData;
    private LiveData<List<Category>> allCategoriesLiveData;

    // constructor to initialise the repository class
    public Repository(Application application) {
        // get reference/instance of the database
        AppDatabase db = AppDatabase.getDatabase(application);

        // get reference to DAO, to perform CRUD operations
        eventDAO = db.eventDAO();
        categoryDAO = db.categoryDAO();

        // once the class is initialised get all the items in the form of LiveData
        allEventsLiveData = eventDAO.getAllEvents();
        allCategoriesLiveData = categoryDAO.getAllCategories();

    }

    /**
     * Repository method to get all cards
     * @return LiveData of type List<Item>
     */
    public LiveData<List<Event>> getAllEvents() {return allEventsLiveData;}
    public LiveData<List<Category>> getAllCategories() {return allCategoriesLiveData;}


    /**
     * Repository method to insert one single item
     * @param event object containing details of new Item to be inserted
     */
    public void insert(Event event, EventAddCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(
                () -> {
                    Category category = categoryDAO.getCategoryById(event.getCategoryID());
                    if (categoryDAO.getCategoryById(event.getCategoryID()) != null) {
                        // Insert the event
                        eventDAO.addEvent(event);
                        // Optionally increment the category count
                        categoryDAO.incrementCategoryCount(event.getCategoryID());
                        // Post success back to the main thread
                        mainHandler.post(() -> callback.onSuccess(event.getEventID(),event));
                    } else {
                        // Post error if the category does not exist
                        mainHandler.post(() -> callback.onError("Category does not exist"));
                    }
                });
    }

    public void insert(Category category) {AppDatabase.databaseWriteExecutor.execute(() -> categoryDAO.addCategory(category));}


    public void deleteAllEvents(){AppDatabase.databaseWriteExecutor.execute(()->eventDAO.deleteAllEvents());}
    public void deleteAllCategories(){AppDatabase.databaseWriteExecutor.execute(()->categoryDAO.deleteAllCategories());}


    public void deleteEvent(Event event){
        AppDatabase.databaseWriteExecutor.execute(
                ()-> {eventDAO.deleteEventById(event.getEventID());
                    categoryDAO.decrementCategoryCount(event.getCategoryID());
                });
    }
}
