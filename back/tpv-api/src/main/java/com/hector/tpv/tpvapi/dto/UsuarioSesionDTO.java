package com.hector.tpv.tpvapi.dto;

public class UsuarioSesionDTO {

    private Long id;
    private String nombre;
    private String rol;

    public Long getId() {
        return id;
    }

    public void setId(Long v) {
        id = v;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String v) {
        nombre = v;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String v) {
        rol = v;
    }
}
