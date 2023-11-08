package com.example.projectesupermercat;

import org.apache.commons.codec.digest.DigestUtils;

public class Usuari {
    private int id;
    private String nom;
    private String cognom;
    private String email;
    private String password;

    public Usuari(int id, String nom, String cognom, String email, String password) {
        this.id = id;
        this.nom = nom;
        this.cognom = cognom;
        this.email = email;
        this.password = DigestUtils.md5Hex(password).toUpperCase();
    }

    public Usuari(int id, String nombre, String apellido, String mail){
        this(id, nombre,apellido,mail,"");
    }

    public Usuari(String mail, String password) {
        this(0,"","",mail,password);
    }

    public Usuari(){

    }
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
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
