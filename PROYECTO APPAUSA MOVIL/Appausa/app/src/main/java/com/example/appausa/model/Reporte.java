package com.example.appausa.model;

public class Reporte {
    private String id,fecha, tipo, estado,doc,empleado, descrip;



    public Reporte(String id, String empleado,String fecha, String tipo, String estado, String descrip, String doc) {
        this.id = id;
        this.empleado = empleado;
        this.fecha = fecha;
        this.tipo = tipo;
        this.estado = estado;
        this.descrip = descrip;
        this.doc = doc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    @Override
    public String toString() {
        return "reporte{" +
                "id='" + id + '\'' +
                ", fecha='" + fecha + '\'' +
                ", tipo='" + tipo + '\'' +
                ", estado='" + estado + '\'' +
                ", doc='" + doc + '\'' +
                ", empleado='" + empleado + '\'' +
                '}';
    }
}
