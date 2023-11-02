package com.example.projectesupermercat;

public class JsonProducte {
    private int id;
    private String nom;
    private float preu;
    private float preuTotal;
    private String imatge;
    private String descripcio;
    private int quantitat;

    // Constructor, getters y setters

    public JsonProducte(int id, String nom, float preu, String imatge, String descripcio, int quantitat) {
        this.id = id;
        this.nom = nom;
        this.preu = preu;
        this.imatge = imatge;
        this.descripcio = descripcio;
        this.quantitat = quantitat;
    }

    public JsonProducte(int id, int quantitat) {
        this.id = id;
        this.quantitat = quantitat;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    public float getPreuTotal() {
        return preuTotal;
    }

    public void setPreuTotal(float preuTotal) {
        this.preuTotal = preuTotal;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }

    public String getImatge() {
        return imatge;
    }

    public void setImatge(String imatge) {
        this.imatge = imatge;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }
}
