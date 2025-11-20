package com.example.comandero.model.dto;

public class MesaAbiertaItemDTO {
    private long productoId;
    private String nombre;
    private long cantidad;
    private double precioUnitario;
    private double ivaTipo;
    private double subtotal;

    public long getProductoId() { return productoId; }
    public void setProductoId(long productoId) { this.productoId = productoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public long getCantidad() { return cantidad; }
    public void setCantidad(long cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public double getIvaTipo() { return ivaTipo; }
    public void setIvaTipo(double ivaTipo) { this.ivaTipo = ivaTipo; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
