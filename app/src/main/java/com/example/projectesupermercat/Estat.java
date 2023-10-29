package com.example.projectesupermercat;

public enum Estat {
    PENDENT_DE_CONFIRMACIO(0),
    EN_PREPARACIO(1),
    LLEST(2),
    RECOLLIT(3),
    INVALID(4);

    private int estat_actual;
    private Estat(int estat_actual){
        this.estat_actual = estat_actual;
    }

    public int getEstat_actual() {
        return estat_actual;
    }

}
