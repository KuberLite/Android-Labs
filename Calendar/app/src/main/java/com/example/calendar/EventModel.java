package com.example.calendar;

import com.google.gson.annotations.Expose;

public class EventModel {
    @Expose(serialize = true, deserialize = true)
    public String dateEvent;

    @Expose(serialize = true, deserialize = true)
    public String event;

    public EventModel(String dateEvent, String event){
        this.event = event;
        this.dateEvent = dateEvent;
    }
}
