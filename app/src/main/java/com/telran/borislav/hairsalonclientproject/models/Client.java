package com.telran.borislav.hairsalonclientproject.models;

/**
 * Created by Boris on 12.04.2017.
 */

public class Client {
    private String clientPhoneNumber;
    private String clientEmail;
    private String clientPassword;
    private String clientName;
    private String clientLastName;

    public Client() {
    }

    public Client(String clientPhoneNumber, String clientEmail, String clientPassword, String clientName, String clientLastName) {
        this.clientPhoneNumber = clientPhoneNumber;
        this.clientEmail = clientEmail;
        this.clientPassword = clientPassword;
        this.clientName = clientName;
        this.clientLastName = clientLastName;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPassword() {
        return clientPassword;
    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }
}
