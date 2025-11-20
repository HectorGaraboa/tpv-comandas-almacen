package com.hector.tpv.tpvapi.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "insumo")
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "unidad_base")
    private String unidadBase;

    @Column(name = "factor_base")
    private BigDecimal factorBase;

    @Column(name = "coste_unit_base")
    private BigDecimal costeUnitBase;

    @Column(name = "stock_actual_base")
    private BigDecimal stockActualBase;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_stock")
    private TipoStock tipoStock;

    public enum TipoStock {
        MEDIBLE, UNITARIO
    }

    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUnidadBase() { return unidadBase; }
    public void setUnidadBase(String unidadBase) { this.unidadBase = unidadBase; }

    public BigDecimal getFactorBase() { return factorBase; }
    public void setFactorBase(BigDecimal factorBase) { this.factorBase = factorBase; }

    public BigDecimal getCosteUnitBase() { return costeUnitBase; }
    public void setCosteUnitBase(BigDecimal costeUnitBase) { this.costeUnitBase = costeUnitBase; }

    public BigDecimal getStockActualBase() { return stockActualBase; }
    public void setStockActualBase(BigDecimal stockActualBase) { this.stockActualBase = stockActualBase; }

    public TipoStock getTipoStock() { return tipoStock; }
    public void setTipoStock(TipoStock tipoStock) { this.tipoStock = tipoStock; }
}
