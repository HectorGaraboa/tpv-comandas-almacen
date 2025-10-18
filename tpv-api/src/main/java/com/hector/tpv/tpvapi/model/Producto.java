package com.hector.tpv.tpvapi.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "producto")
public class Producto {
    public enum Destino { COCINA, BARRA }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "categoria_id")
    private Long categoriaId;

    @Column(name = "precio_venta")
    private BigDecimal precioVenta;

    @Column(name = "iva_tipo")
    private BigDecimal ivaTipo;

    @Enumerated(EnumType.STRING)
    private Destino destino;

    private Integer activo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }
    public BigDecimal getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(BigDecimal precioVenta) { this.precioVenta = precioVenta; }
    public BigDecimal getIvaTipo() { return ivaTipo; }
    public void setIvaTipo(BigDecimal ivaTipo) { this.ivaTipo = ivaTipo; }
    public Destino getDestino() { return destino; }
    public void setDestino(Destino destino) { this.destino = destino; }
    public Integer getActivo() { return activo; }
    public void setActivo(Integer activo) { this.activo = activo; }
}
