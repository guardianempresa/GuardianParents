package com.controlderuta.guardianpadres.model;

/**
 * Created by eduin on 7/09/2017.
 */

public class AlertDistance {

    private int alertone;
    private int alerttwo;
    private int alertthree;
    private String code;
    private String nameroute;


    public AlertDistance() {
    }

    public AlertDistance(int alertone, int alerttwo, int alertthree, String code, String nameroute) {
        this.alertone = alertone;
        this.alerttwo = alerttwo;
        this.alertthree = alertthree;
        this.code = code;
        this.nameroute = nameroute;
    }

    public int getAlertone() {
        return alertone;
    }

    public void setAlertone(int alertone) {
        this.alertone = alertone;
    }

    public int getAlerttwo() {
        return alerttwo;
    }

    public void setAlerttwo(int alerttwo) {
        this.alerttwo = alerttwo;
    }

    public int getAlertthree() {
        return alertthree;
    }

    public void setAlertthree(int alertthree) {
        this.alertthree = alertthree;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameroute() {
        return nameroute;
    }

    public void setNameroute(String nameroute) {
        this.nameroute = nameroute;
    }
}
