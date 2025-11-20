package com.hector.tpv.tpvapi.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "producto_insumo")
public class ProductoInsumo {

    @EmbeddedId
    private ProductoInsumoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productoId")
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("insumoId")
    @JoinColumn(name = "insumo_id")
    private Insumo insumo;

    @Column(name = "cantidad_base_por_ud", nullable = false)
    private BigDecimal cantidadBasePorUd;

    public ProductoInsumoId getId() { return id; }
    public void setId(ProductoInsumoId id) { this.id = id; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public Insumo getInsumo() { return insumo; }
    public void setInsumo(Insumo insumo) { this.insumo = insumo; }

    public BigDecimal getCantidadBasePorUd() { return cantidadBasePorUd; }
    public void setCantidadBasePorUd(BigDecimal cantidadBasePorUd) { this.cantidadBasePorUd = cantidadBasePorUd; }
}
