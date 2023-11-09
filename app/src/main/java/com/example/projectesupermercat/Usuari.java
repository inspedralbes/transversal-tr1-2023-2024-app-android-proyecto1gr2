package com.example.projectesupermercat;

import org.apache.commons.codec.digest.DigestUtils;

public class Usuari {

    private String id;
    private int userId;
    private String nom;
    private String cognoms;
    private String email;
    private String password;

    public Usuari(String nom, String cognoms, String email, String password) {
        this.nom = nom;
        this.cognoms = cognoms;
        this.email = email;
        this.password = DigestUtils.md5Hex(password).toUpperCase();
    }

    public Usuari(String id, String nom, String cognoms, String email, String password) {
        this.id = id;
        this.nom = nom;
        this.cognoms = cognoms;
        this.email = email;
        this.password = DigestUtils.md5Hex(password).toUpperCase();
    }

    public Usuari(String id, int userId, String nom, String cognoms, String email, String password) {
        this.id = id;
        this.userId = userId;
        this.nom = nom;
        this.cognoms = cognoms;
        this.email = email;
        this.password = password;
    }

    public Usuari(String nombre, String apellido, String mail){
        this(nombre,apellido,mail,"");
    }

    public Usuari(String mail, String password) {
        this("","",mail,password);
    }

    public Usuari(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordCypher(String password){
        this.password = DigestUtils.md5Hex(password).toUpperCase();
    }
}
