package com.example.projectesupermercat;

import org.apache.commons.codec.digest.DigestUtils;

public class Usuari {

    private String nombre;
    private String apellido;
    private String mail;
    private String password;

    public Usuari(String nombre, String apellido, String mail, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
