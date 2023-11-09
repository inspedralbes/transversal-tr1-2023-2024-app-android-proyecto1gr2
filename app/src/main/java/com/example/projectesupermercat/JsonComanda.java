package com.example.projectesupermercat;

public class JsonComanda {
    int id;
    int estado;
    float preuTotal;
    String nom;
    String cognoms;
    String email;

    public JsonComanda(int id, int estado, float preuTotal, String nom, String cognoms, String email) {
        this.id = id;
        this.estado = estado;
        this.preuTotal = preuTotal;
        this.nom = nom;
        this.cognoms = cognoms;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
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

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
