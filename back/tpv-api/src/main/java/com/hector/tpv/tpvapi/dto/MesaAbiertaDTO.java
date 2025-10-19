/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hector.tpv.tpvapi.dto;

import java.util.List;

public class MesaAbiertaDTO {
    private Long mesaId;
    private String codigoMesa;
    private double total;
    private List<MesaAbiertaItemDTO> items;

    public MesaAbiertaDTO(Long mesaId, String codigoMesa, double total, List<MesaAbiertaItemDTO> items) {
        this.mesaId = mesaId; this.codigoMesa = codigoMesa; this.total = total; this.items = items;
    }
    public Long getMesaId(){return mesaId;}
    public String getCodigoMesa(){return codigoMesa;}
    public double getTotal(){return total;}
    public List<MesaAbiertaItemDTO> getItems(){return items;}
}