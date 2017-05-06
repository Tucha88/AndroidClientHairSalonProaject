package com.telran.borislav.hairsalonclientproject.models;

import java.util.ArrayList;


/**
 * Created by Boris on 06.05.2017.
 */

public class MasterArray {
    private ArrayList<Master> masters = new ArrayList<>();

    public MasterArray(ArrayList<Master> masters) {
        this.masters = masters;
    }

    public MasterArray() {
    }

    public ArrayList<Master> getMasters() {
        return masters;
    }

    public void setMasters(ArrayList<Master> masters) {
        this.masters = masters;
    }
}
