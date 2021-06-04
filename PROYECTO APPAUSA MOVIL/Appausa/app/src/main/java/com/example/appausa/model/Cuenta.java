package com.example.appausa.model;

public class Cuenta {
    String username, estado, perfil, usuario, ultimologin;

    public Cuenta(String username, String estado, String perfil, String usuario, String ultimologin) {
        this.username = username;
        this.estado = estado;
        this.perfil = perfil;
        this.usuario = usuario;
        this.ultimologin = ultimologin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUltimologin() {
        return ultimologin;
    }

    public void setUltimologin(String ultimologin) {
        this.ultimologin = ultimologin;
    }

    @Override
    public String toString() {

        return "Cuenta: "+username+"\n" +
                "Dueño: "+usuario+" - Estado: "+estado+" - Perfil: "+perfil+" - Ultimo ingreso: "+ultimologin+"";
    }
}
