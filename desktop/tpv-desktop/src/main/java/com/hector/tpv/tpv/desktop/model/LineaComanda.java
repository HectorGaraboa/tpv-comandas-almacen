/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hector.tpv.tpv.desktop.model;

public class LineaComanda {

    private Long productoId;
    private int cantidad;
    private String textoModificador;
    private double precioUnitario;
    private double ivaTipo;

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long v) {
        productoId = v;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int v) {
        cantidad = v;
    }

    public String getTextoModificador() {
        return textoModificador;
    }

    public void setTextoModificador(String v) {
        textoModificador = v;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double v) {
        precioUnitario = v;
    }

    public double getIvaTipo() {
        return ivaTipo;
    }

    public void setIvaTipo(double v) {
        ivaTipo = v;
    }
}
