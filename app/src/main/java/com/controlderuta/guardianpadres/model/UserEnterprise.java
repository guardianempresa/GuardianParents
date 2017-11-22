package com.controlderuta.guardianpadres.model;

/**
 * Created by eduin on 29/08/2017.
 */

public class UserEnterprise {

    private String id;
    private String name;
    private String code;
    private String lastname;
    private float latitud;
    private float longitud;
    private String phone;
    private String messageuser;
    private float distance;
    private String check;
    private String iconface;
    private String icon;

    public UserEnterprise() {
    }

    public UserEnterprise(String id, String name, String code, String lastname, float latitud, float longitud, String phone, String messageuser, float distance, String check, String iconface, String icon) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.lastname = lastname;
        this.latitud = latitud;
        this.longitud = longitud;
        this.phone = phone;
        this.messageuser = messageuser;
        this.distance = distance;
        this.check = check;
        this.iconface = iconface;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessageuser() {
        return messageuser;
    }

    public void setMessageuser(String messageuser) {
        this.messageuser = messageuser;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getIconface() {
        return iconface;
    }

    public void setIconface(String iconface) {
        this.iconface = iconface;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

