package com.example.projectesupermercat;

import android.content.res.ColorStateList;
import android.graphics.Color;

public enum Estat {
    PENDENT_DE_CONFIRMACIO(0,"Pendent de confirmació", Color.rgb(124, 124, 124)),
    EN_PREPARACIO(1,"En preparació",Color.rgb(250, 179, 51)),
    LLEST(2, "Llest", Color.rgb(139, 195, 74)),
    RECOLLIT(3,"Recollit", Color.rgb(33, 150, 243)),
    REBUTJAT(4,"Rebutjat", Color.rgb(244, 67, 54)),
    INVALID(5,"Error", Color.rgb(244, 67, 54));

    private int estat_actual;

    private String estado_text;

    private int color_actual;
    private Estat(int estat_actual, String estado_text, int color_actual){
        this.estat_actual = estat_actual;
        this.estado_text = estado_text;
        this.color_actual = color_actual;
    }

    public int getEstat_actual() {
        return estat_actual;
    }

    public String getEstado_text() {
        return estado_text;
    }

    public int getColor_actual() { return  color_actual; }
}
