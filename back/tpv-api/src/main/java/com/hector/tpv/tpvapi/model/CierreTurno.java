package com.hector.tpv.tpvapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cierre_turno")
public class CierreTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creado_at", nullable = false)
    private LocalDateTime creadoAt;

    @Column(name = "desde", nullable = false)
    private LocalDateTime desde;

    @Column(name = "hasta", nullable = false)
    private LocalDateTime hasta;

    @Column(name = "num_comandas", nullable = false)
    private int numComandas;

    @Column(name = "base_total", nullable = false)
    private double baseTotal;

    @Column(name = "iva_total", nullable = false)
    private double ivaTotal;

    @Column(name = "total", nullable = false)
    private double total;

    public Long getId() {
        return id;
    }

    public void setId(Long v) {
        id = v;
    }

    public LocalDateTime getCreadoAt() {
        return creadoAt;
    }

    public void setCreadoAt(LocalDateTime v) {
        creadoAt = v;
    }

    public LocalDateTime getDesde() {
        return desde;
    }

    public void setDesde(LocalDateTime v) {
        desde = v;
    }

    public LocalDateTime getHasta() {
        return hasta;
    }

    public void setHasta(LocalDateTime v) {
        hasta = v;
    }

    public int getNumComandas() {
        return numComandas;
    }

    public void setNumComandas(int v) {
        numComandas = v;
    }

    public double getBaseTotal() {
        return baseTotal;
    }

    public void setBaseTotal(double v) {
        baseTotal = v;
    }

    public double getIvaTotal() {
        return ivaTotal;
    }

    public void setIvaTotal(double v) {
        ivaTotal = v;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double v) {
        total = v;
    }
}
