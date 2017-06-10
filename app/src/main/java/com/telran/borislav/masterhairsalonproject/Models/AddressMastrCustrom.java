package com.telran.borislav.masterhairsalonproject.Models;

import java.util.ArrayList;

/**
 * Created by Boris on 11.06.2017.
 */

public class AddressMastrCustrom {
    private String address;
    private double latitude;
    private double longitude;
    private String placeId;
    private ArrayList<WeekDayCustom> weekTemplate;

    public AddressMastrCustrom() {
    }

    public AddressMastrCustrom(String address, double latitude, double longitude, String placeId, ArrayList<WeekDayCustom> weekTemplate) {

        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeId = placeId;
        this.weekTemplate = weekTemplate;
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
}
