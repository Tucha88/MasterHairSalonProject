package com.telran.borislav.masterhairsalonproject.Models;

import java.util.ArrayList;

/**
 * Created by Boris on 09.06.2017.
 */

public class Record {
    private LightCalendar calendar;
    private LightClock starTime;
    private ArrayList<Services> services;
    private int duration;
    //TODO Надо переделать на ID клиента. Зачем хранить всего клиента
    private String client;
    private String master;
    private String info;

    public Record() {
    }

    public Record(LightCalendar calendar, LightClock starTime, ArrayList<Services> services, int duration, String client, String master, String info) {
        this.calendar = calendar;
        this.starTime = starTime;
        this.services = services;
        this.duration = duration;
        this.client = client;
        this.master = master;
        this.info = info;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public LightCalendar getCalendar() {

        return calendar;
    }

    public void setCalendar(LightCalendar calendar) {
        this.calendar = calendar;
    }

    public LightClock getStarTime() {
        return starTime;
    }

    public void setStarTime(LightClock starTime) {
        this.starTime = starTime;
    }

    public ArrayList<Services> getServices() {
        return services;
    }

    public void setServices(ArrayList<Services> services) {
        this.services = services;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
