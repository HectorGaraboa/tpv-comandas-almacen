/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hector.tpv.tpv.desktop.model;
import java.util.List;
public class ComandaRequest {
    private Long mesaId;
    private Long camareroId;
    private List<LineaComanda> lineas;
    public Long getMesaId(){return mesaId;}
    public void setMesaId(Long v){mesaId=v;}
    public Long getCamareroId(){return camareroId;}
    public void setCamareroId(Long v){camareroId=v;}
    public List<LineaComanda> getLineas(){return lineas;}
    public void setLineas(List<LineaComanda> v){lineas=v;}
}
