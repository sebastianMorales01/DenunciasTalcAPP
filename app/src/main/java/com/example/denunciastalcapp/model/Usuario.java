package com.example.denunciastalcapp.model;

public class Usuario {
    private String id;
    private String nombre;
    private String uid;
    private String email;
    private String celular;

    public Usuario(){
    }
    public Usuario(String id, String nombre, String uid, String email,String celular) {
        this.id = id;
        this.nombre = nombre;
        this.uid = uid;
        this.email = email;
        this.celular = celular;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
