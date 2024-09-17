package com.example.assignment1_fit2081_32781555.Objects;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class Event{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "eventID")
    private String eventID;
    @ColumnInfo(name = "eventName")
    private String eventName;
    @ColumnInfo(name = "categoryID")
    private String categoryID;
    @ColumnInfo(name = "ticketNumber")
    private int ticketNumber;

    @ColumnInfo(name = "isActive")
    private boolean isActive;

    public Event(String eventID, String categoryID, String eventName, int ticketNumber, boolean isActive) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.categoryID = categoryID;
        this.ticketNumber = ticketNumber;
        this.isActive = isActive;
    }

    //Getter


    public int getId() {return id;}

    public String getEventID() {return eventID;}

    public String getEventName() {return eventName;}

    public String getCategoryID() {return categoryID;}

    public int getTicketNumber() {return ticketNumber;}

    public boolean isActive() {return isActive;}

    //Setter


    public void setId(int id) {this.id = id;}

    public void setEventID(String eventID) {this.eventID = eventID;}

    public void setEventName(String eventName) {this.eventName = eventName;}

    public void setCategoryID(String categoryID) {this.categoryID = categoryID;}

    public void setTicketNumber(int ticketNumber) {this.ticketNumber = ticketNumber;}

    public void setActive(boolean active) {isActive = active;}
}
