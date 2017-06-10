package com.telran.borislav.masterhairsalonproject.Models;

/**
 * Created by vadim on 02.04.2017.
 */

public class Services {
    private String name;
    private int duration; // в минутах
    private int price;
    private String info;

    public Services() {
    }

    public Services(String name, int duration, int price, String info) {
        this.name = name;
        this.duration = duration;
        this.price = price;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
