package com.example.appausa.model;

public class Data {
    private String cuenta, fecha, accion, descrip;

    public Data(String cuenta, String fecha, String accion, String descrip) {
        this.cuenta = cuenta;
        this.fecha = fecha;
        this.accion = accion;
        this.descrip = descrip;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
}
