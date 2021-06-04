package com.example.appausa.model;

public class Usuario {

    private String cc, tipoDoc, pnombre, snombre, papellido, sapellido, fechanam, edad, correoelectronico, telefono, rol;

    public Usuario(String cc, String tipoDoc, String pnombre, String snombre, String papellido, String sapellido, String fechanam, String edad, String correoelectronico, String telefono, String rol) {
        this.cc = cc;
        this.tipoDoc = tipoDoc;
        this.pnombre = pnombre;
        this.snombre = snombre;
        this.papellido = papellido;
        this.sapellido = sapellido;
        this.fechanam = fechanam;
        this.edad = edad;
        this.correoelectronico = correoelectronico;
        this.telefono = telefono;
        this.rol = rol;
    }

    public Usuario(String cc, String tipoDoc, String pnombre, String papellido, String sapellido, String fechanam, String edad, String correoelectronico, String telefono, String rol) {
        this.cc = cc;
        this.tipoDoc = tipoDoc;
        this.pnombre = pnombre;
        this.snombre = "";
        this.papellido = papellido;
        this.sapellido = sapellido;
        this.fechanam = fechanam;
        this.edad = edad;
        this.correoelectronico = correoelectronico;
        this.telefono = telefono;
        this.rol = rol;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getPnombre() {
        return pnombre;
    }

    public void setPnombre(String pnombre) {
        this.pnombre = pnombre;
    }

    public String getSnombre() {
        return snombre;
    }

    public void setSnombre(String snombre) {
        this.snombre = snombre;
    }

    public String getPapellido() {
        return papellido;
    }

    public void setPapellido(String papellido) {
        this.papellido = papellido;
    }

    public String getSapellido() {
        return sapellido;
    }

    public void setSapellido(String sapellido) {
        this.sapellido = sapellido;
    }

    public String getFechanam() {
        return fechanam;
    }

    public void setFechanam(String fechanam) {
        this.fechanam = fechanam;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getCorreoelectronico() {
        return correoelectronico;
    }

    public void setCorreoelectronico(String correoelectronico) {
        this.correoelectronico = correoelectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String toString(){
        if (snombre.equals(null)){
            return tipoDoc+" - "+cc+"\n" +
                    pnombre+" "+papellido+" "+sapellido+"\n" +
                    fechanam+" - "+edad+" años\n" +
                    correoelectronico+" - Tel:"+telefono+"";
        }else {
            return tipoDoc+" - "+cc+"\n" +
                    pnombre+" "+snombre+" "+papellido+" "+sapellido+"\n" +
                    fechanam+" - "+edad+" años\n" +
                    correoelectronico+" - Tel:"+telefono+"";
        }

    }
}
