package com.telran.borislav.hairsalonclientproject.models;

import java.util.ArrayList;

/**
 * Created by Boris on 13.05.2017.
 */

public class ServicesArray {
    private ArrayList<Services> services = new ArrayList<>();

    public ServicesArray(ArrayList<Services> services) {
        this.services = services;
    }

    public ServicesArray() {

    }

    public ArrayList<Services> getServices() {
        return services;
    }

    public void setServices(ArrayList<Services> services) {
        this.services = services;
    }

    public void add(Services services) {
        this.services.add(services);
    }
}
