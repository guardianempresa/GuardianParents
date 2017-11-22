package com.controlderuta.guardianpadres.model;

/**
 * Created by eduin on 30/08/2017.
 */

public class LocRoute {

    private double acumDist;
    private int alertDist;
    private String nombre;
    private int alerta;
    private int estado;
    private double latitud;
    private double longitud;
    private double latitudllegada;
    private double longitudllegada;
    private int time;
    private int tipRoute;

    public LocRoute() {
    }

    public LocRoute(double acumDist, int alertDist, String nombre, int alerta, int estado, double latitud, double longitud, double latitudllegada, double longitudllegada, int time, int tipRoute) {
        this.acumDist = acumDist;
        this.alertDist = alertDist;
        this.nombre = nombre;
        this.alerta = alerta;
        this.estado = estado;
        this.latitud = latitud;
        this.longitud = longitud;
        this.latitudllegada = latitudllegada;
        this.longitudllegada = longitudllegada;
        this.time = time;
        this.tipRoute = tipRoute;
    }

    public double getAcumDist() {
        return acumDist;
    }

    public void setAcumDist(double acumDist) {
        this.acumDist = acumDist;
    }

    public int getAlertDist() {
        return alertDist;
    }

    public void setAlertDist(int alertDist) {
        this.alertDist = alertDist;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAlerta() {
        return alerta;
    }

    public void setAlerta(int alerta) {
        this.alerta = alerta;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitudllegada() {
        return latitudllegada;
    }

    public void setLatitudllegada(double latitudllegada) {
        this.latitudllegada = latitudllegada;
    }

    public double getLongitudllegada() {
        return longitudllegada;
    }

    public void setLongitudllegada(double longitudllegada) {
        this.longitudllegada = longitudllegada;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTipRoute() {
        return tipRoute;
    }

    public void setTipRoute(int tipRoute) {
        this.tipRoute = tipRoute;
    }
}




