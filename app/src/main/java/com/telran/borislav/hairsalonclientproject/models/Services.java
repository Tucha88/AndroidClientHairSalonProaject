package com.telran.borislav.hairsalonclientproject.models;

/**
 * Created by Boris on 06.05.2017.
 */

public class Services {
    private String service;
    private String price;
    private String time;

    public Services() {
    }

    public Services(String service, String price, String time) {
        this.service = service;
        this.price = price;
        this.time = time;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
