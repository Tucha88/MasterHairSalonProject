package com.telran.borislav.masterhairsalonproject.Models;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Boris on 09.06.2017.
 */

public class AddressMaster {
    private String address;
    private double latitude;
    private double longitude;
    private String placeId;
    private ArrayList<WeekDayCustom> weekTemplate;
    private TreeMap<String, CalendarDay> timetableMap;

    public AddressMaster() {
    }


    public AddressMaster(String address, double latitude, double longitude, String placeId, ArrayList<WeekDayCustom> weekTemplate, TreeMap<String, CalendarDay> timetableMap) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeId = placeId;
        this.weekTemplate = weekTemplate;
        this.timetableMap = timetableMap;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public ArrayList<WeekDayCustom> getWeekTemplate() {
        return weekTemplate;
    }

    public void setWeekTemplate(ArrayList<WeekDayCustom> weekTemplate) {
        this.weekTemplate = weekTemplate;
    }

    public TreeMap<String, CalendarDay> getTimetableMap() {
        return timetableMap;
    }

    public void setTimetableMap(TreeMap<String, CalendarDay> timetableMap) {
        this.timetableMap = timetableMap;
    }
}
