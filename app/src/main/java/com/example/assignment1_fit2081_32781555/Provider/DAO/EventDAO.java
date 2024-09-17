package com.example.assignment1_fit2081_32781555.Provider.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.assignment1_fit2081_32781555.Objects.Event;

import java.util.List;

@Dao
public interface EventDAO {
    // // Specifies a database query to retrieve all items from the "items" table. (referenced A.3.4)
    @Query("select * from events")
    LiveData<List<Event>> getAllEvents(); // Returns a LiveData object containing a list of Item objects.

    // Indicates that this method is used to insert data into the database.
    @Insert
    void addEvent(Event event); // Method signature for inserting an Item object into the database.

    @Query("DELETE FROM events")
    void deleteAllEvents();

    @Query("DELETE FROM events WHERE eventID = :eventId")
    void deleteEventById(String eventId);

}
