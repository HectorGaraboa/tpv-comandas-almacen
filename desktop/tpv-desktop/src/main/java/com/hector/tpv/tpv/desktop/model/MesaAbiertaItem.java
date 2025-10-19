/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hector.tpv.tpv.desktop.model;
public class MesaAbiertaItem {
    private Long productoId;
    private String nombre;
    private long cantidad;
    private double precioUnitario;
    private double ivaTipo;
    private double subtotal;
    public Long getProductoId(){return productoId;}
    public void setProductoId(Long v){productoId=v;}
    public String getNombre(){return nombre;}
    public void setNombre(String v){nombre=v;}
    public long getCantidad(){return cantidad;}
    public void setCantidad(long v){cantidad=v;}
    public double getPrecioUnitario(){return precioUnitario;}
    public void setPrecioUnitario(double v){precioUnitario=v;}
    public double getIvaTipo(){return ivaTipo;}
    public void setIvaTipo(double v){ivaTipo=v;}
    public double getSubtotal(){return subtotal;}
    public void setSubtotal(double v){subtotal=v;}
}