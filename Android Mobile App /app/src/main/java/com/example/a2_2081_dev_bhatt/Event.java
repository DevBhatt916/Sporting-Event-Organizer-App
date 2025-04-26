package com.example.a2_2081_dev_bhatt;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "events")
public class Event {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id1")
    private  int id1;

    @ColumnInfo(name = "EventID")
    private String eventID;
    @ColumnInfo(name = "eventName")
    private String eventName;
    @ColumnInfo(name = "CatName")
    private String categoryName;
    @ColumnInfo(name = "ticketsAvailable")
    private int ticketsAvailable;
    @ColumnInfo(name = "EveIsActive")
    private boolean isActive;



    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    //Constructors
    public Event(String eventID, String eventName, String categoryName, int ticketsAvailable, boolean isActive) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.categoryName = categoryName;
        this.ticketsAvailable = ticketsAvailable;
        this.isActive = isActive;

    }

    //Getter
    public String getEventID() {
        return eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    public boolean isActive() {
        return isActive;
    }



    //Setter
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

}


