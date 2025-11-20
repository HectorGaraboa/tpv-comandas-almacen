package com.example.comandero.model;

import java.util.List;

public class MesaAbierta {

    private long mesaId;
    private String codigoMesa;
    private double total;
    private List<MesaAbiertaItem> items;

    public long getMesaId() {
        return mesaId;
    }

    public void setMesaId(long mesaId) {
        this.mesaId = mesaId;
    }

    public String getCodigoMesa() {
        return codigoMesa;
    }

    public void setCodigoMesa(String codigoMesa) {
        this.codigoMesa = codigoMesa;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<MesaAbiertaItem> getItems() {
        return items;
    }

    public void setItems(List<MesaAbiertaItem> items) {
        this.items = items;
    }
}
