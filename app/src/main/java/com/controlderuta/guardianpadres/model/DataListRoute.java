package com.controlderuta.guardianpadres.model;

/**
 * Created by eduin on 3/09/2017.
 */

public class DataListRoute {

    private String code;
    private String nameroute;

    public DataListRoute() {
    }

    public DataListRoute(String code, String nameroute) {
        this.code = code;
        this.nameroute = nameroute;
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
