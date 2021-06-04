package com.example.appausa.model;

public class Historial {
    String id, fecha, cuenta,estado_p, estado_a;

    public Historial(String id, String fecha, String cuenta, String estado_p, String estado_a) {
        this.id = id;
        this.fecha = fecha;
        this.cuenta = cuenta;
        this.estado_p = estado_p;
        this.estado_a = estado_a;
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

    public String getEstado_p() {
        return estado_p;
    }

    public void setEstado_p(String estado_p) {
        this.estado_p = estado_p;
    }

    public String getEstado_a() {
        return estado_a;
    }

    public void setEstado_a(String estado_a) {
        this.estado_a = estado_a;
    }
}
