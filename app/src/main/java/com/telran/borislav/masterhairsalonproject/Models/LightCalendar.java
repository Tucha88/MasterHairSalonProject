package com.telran.borislav.masterhairsalonproject.Models;

/**
 * Created by Boris on 09.06.2017.
 */

public class LightCalendar {
    private int yearLight;
    private int monthLight;
    private int dayLight;

    public LightCalendar() {
    }

    public LightCalendar(int yearLight, int monthLight, int dayLight) {
        this.yearLight = yearLight;
        this.monthLight = monthLight;
        this.dayLight = dayLight;
    }

    public int getYearLight() {
        return yearLight;
    }

    public void setYearLight(int yearLight) {
        this.yearLight = yearLight;
    }

    public int getMonthLight() {
        return monthLight;
    }

    public void setMonthLight(int monthLight) {
        this.monthLight = monthLight;
    }

    public int getDayLight() {
        return dayLight;
    }

    public void setDayLight(int dayLight) {
        this.dayLight = dayLight;
    }
}
