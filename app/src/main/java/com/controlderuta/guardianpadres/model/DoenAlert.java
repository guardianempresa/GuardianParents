package com.controlderuta.guardianpadres.model;

/**
 * Created by eduin on 31/08/2017.
 */

public class DoenAlert {

    private String hour;
    private int type;

    public DoenAlert() {
    }

    public DoenAlert(String hour, int type) {
        this.hour = hour;
        this.type = type;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
