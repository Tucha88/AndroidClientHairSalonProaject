package com.telran.borislav.hairsalonclientproject.models;

/**
 * Created by Boris on 12.04.2017.
 */

public class ClientAuthType {
    private String clientEmail;
    private String clientPassword;

    public ClientAuthType(String clientEmail, String clientPassword) {
        this.clientEmail = clientEmail;
        this.clientPassword = clientPassword;
    }

    public ClientAuthType() {
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
}
