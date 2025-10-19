/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hector.tpv.tpv.desktop.model;

public class Mesa {

    private Long id;
    private String codigo;
    private Long zonaId;

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

    @Override
    public String toString() {
        return codigo;
    }
}
