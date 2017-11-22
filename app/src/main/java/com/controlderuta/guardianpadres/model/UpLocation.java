package com.controlderuta.guardianpadres.model;

/**
 * Created by diego on 04/07/2017.
 */

public class UpLocation {
    private String id;
    private double latitudup;
    private double longitudup;
    private String code;
    private String nombre;
    private String apellido;
    private String contac1;
    private String contac2;
    private String inidist;



    public UpLocation() {
    }

    public UpLocation(String id, double latitudup, double longitudup, String code, String nombre, String apellido, String contac1, String contac2, String inidist) {
        this.id = id;
        this.latitudup = latitudup;
        this.longitudup = longitudup;
        this.code = code;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contac1 = contac1;
        this.contac2 = contac2;
        this.inidist = inidist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitudup() {
        return latitudup;
    }

    public void setLatitudup(double latitudup) {
        this.latitudup = latitudup;
    }

    public double getLongitudup() {
        return longitudup;
    }

    public void setLongitudup(double longitudup) {
        this.longitudup = longitudup;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContac1() {
        return contac1;
    }

    public void setContac1(String contac1) {
        this.contac1 = contac1;
    }

    public String getContac2() {
        return contac2;
    }

    public void setContac2(String contac2) {
        this.contac2 = contac2;
    }

    public String getInidist() {return inidist;}

    public void setInidist(String inidist) {this.inidist = inidist;}
}