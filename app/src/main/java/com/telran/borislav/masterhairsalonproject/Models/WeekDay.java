package com.telran.borislav.masterhairsalonproject.Models;

/**
 * Created by Boris on 09.06.2017.
 */

public class WeekDay {
    private boolean activeDay;
    private String startWork;
    private String endWork;

    public WeekDay(boolean activeDay, String startWork, String endWork) {
        this.activeDay = activeDay;
        this.startWork = startWork;
        this.endWork = endWork;
    }

    public WeekDay() {

    }

    public boolean isActiveDay() {
        return activeDay;
    }

    public void setActiveDay(boolean activeDay) {
        this.activeDay = activeDay;
    }

    public String getStartWork() {
        return startWork;
    }

    public void setStartWork(String startWork) {
        this.startWork = startWork;
    }

    public String getEndWork() {
        return endWork;
    }

    public void setEndWork(String endWork) {
        this.endWork = endWork;
    }
}
