/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hector.tpv.tpv.desktop.model;

public class Producto {
    private Long id;
    private String nombre;
    private Long categoriaId;
    private double precioVenta;
    private double ivaTipo;
    private String destino;
    private int activo;
    public Long getId(){return id;}
    public void setId(Long v){id=v;}
    public String getNombre(){return nombre;}
    public void setNombre(String v){nombre=v;}
    public Long getCategoriaId(){return categoriaId;}
    public void setCategoriaId(Long v){categoriaId=v;}
    public double getPrecioVenta(){return precioVenta;}
    public void setPrecioVenta(double v){precioVenta=v;}
    public double getIvaTipo(){return ivaTipo;}
    public void setIvaTipo(double v){ivaTipo=v;}
    public String getDestino(){return destino;}
    public void setDestino(String v){destino=v;}
    public int getActivo(){return activo;}
    public void setActivo(int v){activo=v;}
    @Override public String toString(){return nombre+" ("+precioVenta+"€)";}
}