package com.telran.borislav.masterhairsalonproject.Models;

import java.util.ArrayList;

/**
 * Created by Boris on 09.06.2017.
 */

public class CalendarDay {

    private LightCalendar myCalendar;
    private LightClock startWork;
    private LightClock endWork;
    private boolean working; // рабочий ли день
    private ArrayList<Record> records;

    public CalendarDay() {
    }

    public CalendarDay(LightCalendar myCalendar, LightClock startWork, LightClock endWork, boolean working, ArrayList<Record> records) {
        this.myCalendar = myCalendar;
        this.startWork = startWork;
        this.endWork = endWork;
        this.working = working;
        this.records = records;
    }

    public LightCalendar getMyCalendar() {
        return myCalendar;
    }

    public void setMyCalendar(LightCalendar myCalendar) {
        this.myCalendar = myCalendar;
    }

    public LightClock getStartWork() {
        return startWork;
    }

    public void setStartWork(LightClock startWork) {
        this.startWork = startWork;
    }

    public LightClock getEndWork() {
        return endWork;
    }

    public void setEndWork(LightClock endWork) {
        this.endWork = endWork;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }
}
