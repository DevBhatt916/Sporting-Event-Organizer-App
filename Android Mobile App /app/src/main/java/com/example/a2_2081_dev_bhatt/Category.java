package com.example.a2_2081_dev_bhatt;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class Category {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id2")
    private  int id2;

    @ColumnInfo(name = "CategoryID")
    private String categoryID;
    @ColumnInfo(name = "CategoryName")
    private String categoryName;
    @ColumnInfo(name = "EventCount")
    private int eventCount;
    @ColumnInfo(name = "isActive")
    private boolean isActive;

    @ColumnInfo(name = "Location")
    private String location;

    //Getter and Setter for id2
    public int getId2() {
        return id2;
    }
    public void setId2(int id2) {
        this.id2 = id2;
    }




    public Category(String categoryID, String categoryName, int eventCount, boolean isActive, String location) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.eventCount = eventCount;
        this.isActive = isActive;
        this.location = location;
    }

    //Getter
    public String getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getEventCount() {
        return eventCount;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public String getLocation() {
        return location;
    }

    //Setter
    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
