package com.example.projectesupermercat;

import java.util.List;

public class Comanda {

    private List<JsonProducte> productes;
    private String email;
    private int estado;
    private  Estat estat;
    private float preuTotal;

    public Comanda(List<JsonProducte> productes, String email, Estat estat, float preuTotal) {
        this.productes = productes;
        this.email = email;
        this.estat = estat;
        this.estado = estat.getEstat_actual();
        this.preuTotal = preuTotal;
    }

    public List<JsonProducte> getProductes() {
        return productes;
    }

    public void setProductes(List<JsonProducte> productes) {
        this.productes = productes;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Estat getEstat() {
        return estat;
    }

    public void setEstat(Estat estat) {
        this.estat = estat;
    }

    public float getPreuTotal() {
        return preuTotal;
    }

    public void setPreuTotal(float preuTotal) {
        this.preuTotal = preuTotal;
    }

    public Estat recibirEstat(int num){
        if(Estat.values().length-1 >= num){
            return Estat.INVALID;
        }
        return Estat.values()[num];
    }
}

