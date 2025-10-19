package com.hector.tpv.tpv.desktop.model;

import java.util.List;

public class MesaAbierta {

    private Long mesaId;
    private String codigoMesa;
    private double total;
    private List<MesaAbiertaItem> items;

    public Long getMesaId() {
        return mesaId;
    }

    public void setMesaId(Long v) {
        mesaId = v;
    }

    public String getCodigoMesa() {
        return codigoMesa;
    }

    public void setCodigoMesa(String v) {
        codigoMesa = v;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double v) {
        total = v;
    }

    public List<MesaAbiertaItem> getItems() {
        return items;
    }

    public void setItems(List<MesaAbiertaItem> v) {
        items = v;
    }
}
