package com.controlderuta.guardianpadres.model;

/**
 * Created by eduin on 10/09/2017.
 */

public class StateAlert {

    private int estado;
    private int tipo;
    private String hourini;
    private String hourfin;

    public StateAlert() {
    }

    public StateAlert(int estado, int tipo, String hourini, String hourfin) {
        this.estado = estado;
        this.tipo = tipo;
        this.hourini = hourini;
        this.hourfin = hourfin;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getHourini() {
        return hourini;
    }

    public void setHourini(String hourini) {
        this.hourini = hourini;
    }

    public String getHourfin() {
        return hourfin;
    }

    public void setHourfin(String hourfin) {
        this.hourfin = hourfin;
    }
}