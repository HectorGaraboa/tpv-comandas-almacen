package com.example.comandero.model.dto;

import java.util.List;

public class MesaAbiertaDTO {
    private long mesaId;
    private String codigoMesa;
    private double total;
    private List<MesaAbiertaItemDTO> items;

    public long getMesaId() { return mesaId; }
    public void setMesaId(long mesaId) { this.mesaId = mesaId; }

    public String getCodigoMesa() { return codigoMesa; }
    public void setCodigoMesa(String codigoMesa) { this.codigoMesa = codigoMesa; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public List<MesaAbiertaItemDTO> getItems() { return items; }
    public void setItems(List<MesaAbiertaItemDTO> items) { this.items = items; }
}
