package com.telran.borislav.hairsalonclientproject.models;

/**
 * Created by Boris on 12.04.2017.
 */

public class ClientAuthType {
    private String email;
    private String password;

    public ClientAuthType(String clientEmail, String clientPassword) {
        this.email = clientEmail;
        this.password = clientPassword;
    }

    public ClientAuthType() {
    }

    public String getClientEmail() {
        return email;
    }

    public void setClientEmail(String clientEmail) {
        this.email = clientEmail;
    }

    public String getClientPassword() {
        return password;
    }

    public void setClientPassword(String clientPassword) {
        this.password = clientPassword;
    }
}
