/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hector.tpv.tpvapi.dto;

public class MesaDTO {

    private Long id;
    private String codigo;
    private Long zonaId;
    private boolean ocupada;

    public MesaDTO(Long id, String codigo, Long zonaId, boolean ocupada) {
        this.id = id;
        this.codigo = codigo;
        this.zonaId = zonaId;
        this.ocupada = ocupada;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Long getZonaId() {
        return zonaId;
    }
}
