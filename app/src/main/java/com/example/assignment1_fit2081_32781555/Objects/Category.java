package com.example.assignment1_fit2081_32781555.Objects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "categories")
public class Category{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "categoryID")
    private String categoryID;

    @ColumnInfo (name =  "categoryName")
    private String categoryName;

    @ColumnInfo (name =  "eventCount")
    private int eventCount;
    @ColumnInfo (name =  "eventLocation")
    private String eventLocation;

    @ColumnInfo(name = "isActive")
    private boolean isActive;

    public Category(String categoryID, String categoryName, int eventCount, boolean isActive, String eventLocation) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.eventCount = eventCount;
        this.isActive = isActive;
        this.eventLocation = eventLocation;
    }

    //Getter


    public int getId() {return id;}
    public String getCategoryID() {return categoryID;}
    public String getCategoryName() {return categoryName;}
    public int getEventCount() {return eventCount;}
    public String getEventLocation() {return eventLocation;}
    public boolean isActive() {return isActive;}

    //Setter

    public void setCategoryID(String categoryID) {this.categoryID = categoryID;}
    public void setCategoryName(String categoryName) {this.categoryName = categoryName;}
    public void setEventCount(int eventCount) {this.eventCount = eventCount;}
    public void setEventLocation(String eventLocation) {this.eventLocation = eventLocation;}
    public void setActive(boolean active) {isActive = active;}

    public void setId(int id) {this.id = id;}
}
