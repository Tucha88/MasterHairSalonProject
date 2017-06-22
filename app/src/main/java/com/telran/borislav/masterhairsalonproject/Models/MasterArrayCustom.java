package com.telran.borislav.masterhairsalonproject.Models;

import java.util.ArrayList;

/**
 * Created by Boris on 21.06.2017.
 */

public class MasterArrayCustom {
    private ArrayList<Master> masters = new ArrayList<>();

    public MasterArrayCustom() {
    }

    public MasterArrayCustom(ArrayList<Master> masters) {

        this.masters = masters;
    }

    public ArrayList<Master> getMasters() {

        return masters;
    }

    public void setMasters(ArrayList<Master> masters) {
        this.masters = masters;
    }
}
