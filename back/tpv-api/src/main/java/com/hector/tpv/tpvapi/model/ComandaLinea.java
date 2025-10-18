package com.hector.tpv.tpvapi.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "comanda_linea")
public class ComandaLinea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="comanda_id")
    private Long comandaId;

    @Column(name="producto_id")
    private Long productoId;

    private Integer cantidad;

    @Column(name="texto_modificador")
    private String textoModificador;

    private Integer anulada;

    @Column(name="precio_unitario")
    private BigDecimal precioUnitario;

    @Column(name="iva_tipo")
    private BigDecimal ivaTipo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getComandaId() { return comandaId; }
    public void setComandaId(Long comandaId) { this.comandaId = comandaId; }
    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public String getTextoModificador() { return textoModificador; }
    public void setTextoModificador(String textoModificador) { this.textoModificador = textoModificador; }
    public Integer getAnulada() { return anulada; }
    public void setAnulada(Integer anulada) { this.anulada = anulada; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    public BigDecimal getIvaTipo() { return ivaTipo; }
    public void setIvaTipo(BigDecimal ivaTipo) { this.ivaTipo = ivaTipo; }
}
