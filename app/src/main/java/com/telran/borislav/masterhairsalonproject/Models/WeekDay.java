package com.telran.borislav.masterhairsalonproject.Models;

/**
 * Created by Boris on 09.06.2017.
 */

public class WeekDay {
    private boolean activeDay;
    private LightClock startWork;
    private LightClock endWork;

    public WeekDay() {

    }

    public WeekDay(boolean activeDay, LightClock startWork, LightClock endWork) {
        this.activeDay = activeDay;
        this.startWork = startWork;
        this.endWork = endWork;
    }

    public boolean isActiveDay() {
        return activeDay;
    }

    public void setActiveDay(boolean activeDay) {
        this.activeDay = activeDay;
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
}
