package com.hector.tpv.tpv.desktop.model;

import java.time.LocalDateTime;

public class Ticket {
    private Long id;
    private Long comandaId;
    private Double baseTotal;
    private Double ivaTotal;
    private Double total;
    private String metodoPago;
    private LocalDateTime creadoAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getComandaId() { return comandaId; }
    public void setComandaId(Long comandaId) { this.comandaId = comandaId; }
    public Double getBaseTotal() { return baseTotal; }
    public void setBaseTotal(Double baseTotal) { this.baseTotal = baseTotal; }
    public Double getIvaTotal() { return ivaTotal; }
    public void setIvaTotal(Double ivaTotal) { this.ivaTotal = ivaTotal; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public LocalDateTime getCreadoAt() { return creadoAt; }
    public void setCreadoAt(LocalDateTime creadoAt) { this.creadoAt = creadoAt; }
}
