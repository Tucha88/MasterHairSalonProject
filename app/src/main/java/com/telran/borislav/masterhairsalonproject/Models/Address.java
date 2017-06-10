package com.telran.borislav.masterhairsalonproject.Models;

/**
 * Created by vadim on 02.04.2017.
 */

public class Address {
    private String adress;
    private String note;

    public Address() {
    }

    public Address(String adress, String note) {
        this.adress = adress;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
