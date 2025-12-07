package com.hector.tpv.tpv.desktop.model;

import java.time.LocalDateTime;

public class CierreTurno {

    private Long id;
    private LocalDateTime creadoAt;
    private LocalDateTime desde;
    private LocalDateTime hasta;
    private int numComandas;
    private double baseTotal;
    private double ivaTotal;
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
