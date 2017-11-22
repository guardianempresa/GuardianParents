package com.controlderuta.guardianpadres.model;

/**
 * Created by eduin on 28/08/2017.
 */

public class UserTravel {

    private String id;
    private String code;
    private float latitud;
    private float longitud;
    private String messageuser;
    private float distance;
    private String check;
    private String iconface;
    private String childname;
    private String childlastname;
    private String icon;
    private String name;
    private String lastname;
    private String phone;

    public UserTravel() {
    }

    public UserTravel(String id, String code, float latitud, float longitud, String messageuser, float distance, String check, String iconface, String childname, String childlastname, String icon, String name, String lastname, String phone) {
        this.id = id;
        this.code = code;
        this.latitud = latitud;
        this.longitud = longitud;
        this.messageuser = messageuser;
        this.distance = distance;
        this.check = check;
        this.iconface = iconface;
        this.childname = childname;
        this.childlastname = childlastname;
        this.icon = icon;
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getChildname() {
        return childname;
    }

    public void setChildname(String childname) {
        this.childname = childname;
    }

    public String getChildlastname() {
        return childlastname;
    }

    public void setChildlastname(String childlastname) {
        this.childlastname = childlastname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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