package com.example.projectesupermercat;

import org.apache.commons.codec.digest.DigestUtils;

public class Usuari {

    private String nom;
    private String cognom;
    private String email;
    private String password;

    public Usuari(String nom, String cognom, String email, String password) {
        this.nom = nom;
        this.cognom = cognom;
        this.email = email;
        this.password = DigestUtils.md5Hex(password).toUpperCase();
    }

    public Usuari(String nombre, String apellido, String mail){
        this(nombre,apellido,mail,"");
    }

    public Usuari(String mail, String password) {
        this("","",mail,password);
    }

    public Usuari(){

    }

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
