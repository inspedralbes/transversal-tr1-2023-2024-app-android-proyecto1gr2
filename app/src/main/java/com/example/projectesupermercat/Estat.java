package com.example.projectesupermercat;

public enum Estat {
    PENDENT_DE_CONFIRMACIO(0,"Pendent de confirmació"),
    EN_PREPARACIO(1,"En preparació"),
    LLEST(2, "Llest"),
    RECOLLIT(3,"Recollit"),
    REBUTJAT(4,"Rebutjat"),
    INVALID(5,"Error");

    private int estat_actual;

    private String estado_text;
    private Estat(int estat_actual, String estado_text){
        this.estat_actual = estat_actual;
        this.estado_text = estado_text;
    }

    public int getEstat_actual() {
        return estat_actual;
    }

    public String getEstado_text() {
        return estado_text;
    }
}
