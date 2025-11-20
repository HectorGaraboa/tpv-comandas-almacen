package com.hector.tpv.tpvapi.dto;

import com.hector.tpv.tpvapi.model.Ticket;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TicketDTO {
    private Long id;
    private Long comandaId;
    private BigDecimal baseTotal;
    private BigDecimal ivaTotal;
    private BigDecimal total;
    private String metodoPago;
    private LocalDateTime creadoAt;

    public static TicketDTO fromEntity(Ticket t) {
        TicketDTO x = new TicketDTO();
        x.id = t.getId();
        x.comandaId = t.getComandaId();
        x.baseTotal = BigDecimal.valueOf(t.getBaseTotal());
        x.ivaTotal = BigDecimal.valueOf(t.getIvaTotal());
        x.total = BigDecimal.valueOf(t.getTotal());
        x.metodoPago = t.getMetodoPago() == null ? null : t.getMetodoPago().name();
        x.creadoAt = t.getCreadoAt();
        return x;
    }

    public Long getId() { return id; }
    public Long getComandaId() { return comandaId; }
    public BigDecimal getBaseTotal() { return baseTotal; }
    public BigDecimal getIvaTotal() { return ivaTotal; }
    public BigDecimal getTotal() { return total; }
    public String getMetodoPago() { return metodoPago; }
    public LocalDateTime getCreadoAt() { return creadoAt; }
}
