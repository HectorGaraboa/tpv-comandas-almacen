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
@Table(name = "receta_insumo")
public class RecetaInsumo {

    @EmbeddedId
    private RecetaInsumoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recetaId")
    @JoinColumn(name = "receta_id")
    private Receta receta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("insumoId")
    @JoinColumn(name = "insumo_id")
    private Insumo insumo;

    @Column(name = "cantidad_base", nullable = false)
    private BigDecimal cantidadBase;

    public RecetaInsumoId getId() { return id; }
    public void setId(RecetaInsumoId id) { this.id = id; }

    public Receta getReceta() { return receta; }
    public void setReceta(Receta receta) { this.receta = receta; }

    public Insumo getInsumo() { return insumo; }
    public void setInsumo(Insumo insumo) { this.insumo = insumo; }

    public BigDecimal getCantidadBase() { return cantidadBase; }
    public void setCantidadBase(BigDecimal cantidadBase) { this.cantidadBase = cantidadBase; }
}
