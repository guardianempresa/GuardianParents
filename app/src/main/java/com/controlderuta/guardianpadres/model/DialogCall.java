package com.controlderuta.guardianpadres.model;

/**
 * Created by eduin on 3/09/2017.
 */

public class DialogCall {

    private String nameconductor;
    private String lastnameconductor;
    private String mobileconductor;

    public DialogCall() {
    }

    public DialogCall(String nameconductor, String lastnameconductor, String mobileconductor) {
        this.nameconductor = nameconductor;
        this.lastnameconductor = lastnameconductor;
        this.mobileconductor = mobileconductor;
    }

    public String getNameconductor() {
        return nameconductor;
    }

    public void setNameconductor(String nameconductor) {
        this.nameconductor = nameconductor;
    }

    public String getLastnameconductor() {
        return lastnameconductor;
    }

    public void setLastnameconductor(String lastnameconductor) {
        this.lastnameconductor = lastnameconductor;
    }

    public String getMobileconductor() {
        return mobileconductor;
    }

    public void setMobileconductor(String mobileconductor) {
        this.mobileconductor = mobileconductor;
    }
}

