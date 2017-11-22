package com.controlderuta.guardianpadres.model;

/**
 * Created by eduin on 12/07/2017.
 */

public class User {

    private String name;
    private String lastname;
    private String phone;

    public User() {
    }

    public User(String name, String lastname, String phone) {
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}