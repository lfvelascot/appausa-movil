package com.example.appausa.model;

public class Comentario {
    private String id,fecha,cuenta,contenido;

    public Comentario(String id, String fecha, String cuenta, String contenido) {
        this.id = id;
        this.fecha = fecha;
        this.cuenta = cuenta;
        this.contenido = contenido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "ID: "+id+" Comentario enviado "+fecha+" por "+cuenta+":\n"+contenido;
    }
}
