package com.example.projectesupermercat;

import java.io.Serializable;

public class Producte implements Serializable {
    private int id;
    private String nom;
    private String descripcio;
    private float preu;
    private int quantitat;
    private String imatge;
    private int id_categoria;
    private String catNom;

    public Producte(int id, String nom, String descripcio, float preu, int quantitat, String imatge, int id_categoria, String catNom) {
        this.id = id;
        this.nom = nom;
        this.descripcio = descripcio;
        this.preu = preu;
        this.quantitat = quantitat;
        this.imatge = imatge;
        this.id_categoria = id_categoria;
        this.catNom = catNom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    public String getImatge() {
        return imatge;
    }

    public void setImatge(String imatge) {
        this.imatge = imatge;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getCatNom() {
        return catNom;
    }

    public void setCatNom(String catNom) {
        this.catNom = catNom;
    }
}
