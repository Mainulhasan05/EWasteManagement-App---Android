package com.example.ewastemanagement;

public class EventModel {
    private String eventname,place,date,time,description;

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventModel(String eventname, String place, String date, String time, String description) {
        this.eventname = eventname;
        this.place = place;
        this.date = date;
        this.time = time;
        this.description = description;
    }
}
