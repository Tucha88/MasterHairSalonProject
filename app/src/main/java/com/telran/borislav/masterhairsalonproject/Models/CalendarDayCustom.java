package com.telran.borislav.masterhairsalonproject.Models;

import java.util.ArrayList;

/**
 * Created by Boris on 11.06.2017.
 */

public class CalendarDayCustom {
    private String myCalendar;
    private LightClock startWork;
    private LightClock endWork;
    private boolean working; // рабочий ли день
    private ArrayList<RecordCustom> records;

    public CalendarDayCustom() {
    }

    public CalendarDayCustom(String myCalendar, LightClock startWork, LightClock endWork, boolean working, ArrayList<RecordCustom> records) {

        this.myCalendar = myCalendar;
        this.startWork = startWork;
        this.endWork = endWork;
        this.working = working;
        this.records = records;
    }

    public String getMyCalendar() {

        return myCalendar;
    }

    public void setMyCalendar(String myCalendar) {
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

    public ArrayList<RecordCustom> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<RecordCustom> records) {
        this.records = records;
    }
}
