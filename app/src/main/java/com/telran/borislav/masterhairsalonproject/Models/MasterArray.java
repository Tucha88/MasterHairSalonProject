package com.telran.borislav.masterhairsalonproject.Models;

import java.util.ArrayList;


/**
 * Created by Boris on 06.05.2017.
 */

public class MasterArray {
    private ArrayList<MasterCustom> masters = new ArrayList<>();

    public MasterArray(ArrayList<MasterCustom> masters) {
        this.masters = masters;
    }

    public MasterArray() {
    }

    public ArrayList<MasterCustom> getMasters() {
        return masters;
    }

    public void setMasters(ArrayList<MasterCustom> masters) {
        this.masters = masters;
    }
}
