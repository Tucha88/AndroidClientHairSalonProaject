package com.telran.borislav.hairsalonclientproject.models;

import java.util.ArrayList;

/**
 * Created by Boris on 06.05.2017.
 */

public class Master {
    private String email;
    private String phoneNumber;
    private String password;
    private String name;
    private String lastName;
    private ArrayList<String> lang;
    private String masterType;
    private ArrayList<Services> serivce = new ArrayList<>();
    private String addresses;

    public Master() {
    }

    public Master(String email, String phoneNumber, String password, String name, String lastName, ArrayList<String> lang, String masterType, ArrayList<Services> serivce, String addresses) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.lang = lang;
        this.masterType = masterType;
        this.serivce = serivce;
        this.addresses = addresses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<String> getLang() {
        return lang;
    }

    public void setLang(ArrayList<String> lang) {
        this.lang = lang;
    }

    public String getMasterType() {
        return masterType;
    }

    public void setMasterType(String masterType) {
        this.masterType = masterType;
    }

    public ArrayList<Services> getSerivce() {
        return serivce;
    }

    public void setSerivce(ArrayList<Services> serivce) {
        this.serivce = serivce;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }
}
