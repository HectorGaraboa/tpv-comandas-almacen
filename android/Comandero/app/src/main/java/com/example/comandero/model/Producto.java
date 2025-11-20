package com.example.comandero.model;

public class Producto {
    private Long id;
    private String nombre;
    private Long categoriaId;
    private double precioVenta;
    private double ivaTipo;
    private String destino;
    private int activo;

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Long getCategoriaId() { return categoriaId; }
    public double getPrecioVenta() { return precioVenta; }
    public double getIvaTipo() { return ivaTipo; }
    public String getDestino() { return destino; }
    public int getActivo() { return activo; }

    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }
    public void setIvaTipo(double ivaTipo) { this.ivaTipo = ivaTipo; }
    public void setDestino(String destino) { this.destino = destino; }
    public void setActivo(int activo) { this.activo = activo; }
}
