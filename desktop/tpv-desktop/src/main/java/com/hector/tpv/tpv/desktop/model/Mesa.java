package com.hector.tpv.tpv.desktop.model;

public class Mesa {

    private Long id;
    private String codigo;
    private Long zonaId;
    private boolean ocupada;

    public Long getId() {
        return id;
    }

    public void setId(Long v) {
        id = v;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String v) {
        codigo = v;
    }

    public Long getZonaId() {
        return zonaId;
    }

    public void setZonaId(Long v) {
        zonaId = v;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean v) {
        ocupada = v;
    }

    @Override
    public String toString() {
        return codigo;
    }
}
