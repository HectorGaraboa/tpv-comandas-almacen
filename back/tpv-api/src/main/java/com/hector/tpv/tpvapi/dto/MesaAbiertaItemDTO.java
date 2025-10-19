/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hector.tpv.tpvapi.dto;

public class MesaAbiertaItemDTO {
    private Long productoId;
    private String nombre;
    private long cantidad;
    private double precioUnitario;
    private double ivaTipo;
    private double subtotal;

    public MesaAbiertaItemDTO(Long productoId, String nombre, long cantidad, double precioUnitario, double ivaTipo, double subtotal) {
        this.productoId = productoId; this.nombre = nombre; this.cantidad = cantidad;
        this.precioUnitario = precioUnitario; this.ivaTipo = ivaTipo; this.subtotal = subtotal;
    }
    public Long getProductoId(){return productoId;}
    public String getNombre(){return nombre;}
    public long getCantidad(){return cantidad;}
    public double getPrecioUnitario(){return precioUnitario;}
    public double getIvaTipo(){return ivaTipo;}
    public double getSubtotal(){return subtotal;}
}
