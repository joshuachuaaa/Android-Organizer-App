package com.example.assignment1_fit2081_32781555.Provider.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.assignment1_fit2081_32781555.Objects.Event;
import com.example.assignment1_fit2081_32781555.Provider.Repository;
import com.example.assignment1_fit2081_32781555.Utility.EventAddCallback;

import java.util.List;


/**
 * ViewModel class is used for pre-processing the data,
 * before passing it to the controllers (Activity or Fragments). ViewModel class should not hold
 * direct reference to database. ViewModel class relies on repository class, hence the database is
 * accessed using the Repository class.
 */
public class EventViewModel extends AndroidViewModel {
    // reference to CardRepository
    private Repository repository;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<Event>> allEventsLiveData;

    public EventViewModel(@NonNull Application application) {
        super(application);

        // get reference to the repository class
        repository = new Repository(application);

        // get all items by calling method defined in repository class
        allEventsLiveData = repository.getAllEvents();
    }

    /**
     * ViewModel method to get all Events
     * @return LiveData of type List<Event>
     */
    public LiveData<List<Event>> getAllEvents() {
        return allEventsLiveData;
    }

    /**
     * ViewModel method to insert one single item,
     * usually calling insert method defined in repository class
     * @param event object containing details of new Item to be inserted
     */
    public void insert(Event event, EventAddCallback eventAddCallBack) {
        repository.insert(event,eventAddCallBack);

    }

    public void deleteAllEvents(){repository.deleteAllEvents();}

    public void deleteEvent(Event event){repository.deleteEvent(event);}

}
