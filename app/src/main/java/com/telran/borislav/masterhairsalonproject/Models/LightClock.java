package com.telran.borislav.masterhairsalonproject.Models;

/**
 * Created by Boris on 09.06.2017.
 */

public class LightClock {
    private int hourLight;
    private int minuteLight;

    public LightClock(int hourLight, int minuteLight) {
        this.hourLight = hourLight;
        this.minuteLight = minuteLight;
    }

    public LightClock() {

    }

    public int getHourLight() {

        return hourLight;
    }

    public void setHourLight(int hourLight) {
        this.hourLight = hourLight;
    }

    public int getMinuteLight() {
        return minuteLight;
    }

    public void setMinuteLight(int minuteLight) {
        this.minuteLight = minuteLight;
    }
}
