package com.telran.borislav.masterhairsalonproject.Models;

/**
 * Created by Boris on 10.06.2017.
 */

public class WeekDayCustom {
    private boolean activeDay;
    private LightClock startWork;
    private LightClock endWork;

    public WeekDayCustom() {
    }

    public WeekDayCustom(boolean activeDay, LightClock startWork, LightClock endWork) {

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

    public WeekDayCustom nWeekDayCustom(WeekDay weekDay) {
        WeekDayCustom weekDayCustom = new WeekDayCustom();
        String[] strStartWork = weekDay.getStartWork().split(":");
        weekDayCustom.setStartWork(new LightClock(Integer.valueOf(strStartWork[0]), Integer.valueOf(strStartWork[1])));
        String[] strEndWork = weekDay.getEndWork().split(":");
        weekDayCustom.setEndWork(new LightClock(Integer.valueOf(strEndWork[0]), Integer.valueOf(strEndWork[1])));
        weekDayCustom.activeDay = weekDay.isActiveDay();
        return weekDayCustom;
    }
}
