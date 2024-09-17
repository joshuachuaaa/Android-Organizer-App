package com.example.assignment1_fit2081_32781555.Provider.DAO;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.assignment1_fit2081_32781555.Objects.Category;

import java.util.List;

@Dao
public interface CategoryDAO {
    // // Specifies a database query to retrieve all items from the "items" table. (referenced A.3.4)
    @Query("select * from categories")
    LiveData<List<Category>> getAllCategories(); // Returns a LiveData object containing a list of Item objects.

    // Indicates that this method is used to insert data into the database.
    @Insert
    void addCategory(Category category); // Method signature for inserting an Item object into the database.

    @Query("DELETE FROM categories")
    void deleteAllCategories();

    @Query("SELECT * FROM categories WHERE categoryID = :categoryId")
    Category getCategoryById(String categoryId);


    @Query("UPDATE categories SET eventCount = eventCount + 1 WHERE categoryID = :categoryId")
    void incrementCategoryCount(String categoryId);

    @Query("UPDATE categories SET eventCount = eventCount - 1 WHERE categoryID = :categoryId")
    void decrementCategoryCount(String categoryId);

}
