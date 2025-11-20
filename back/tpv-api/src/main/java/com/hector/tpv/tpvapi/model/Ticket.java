
package com.hector.tpv.tpvapi.model;



import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comanda_id")
    private Long comandaId;

    @Column(name = "base_total")
    private Double baseTotal;

    @Column(name = "iva_total")
    private Double ivaTotal;

    @Column(name = "total")
    private Double total;

    @Column(name = "creado_at")
    private LocalDateTime creadoAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;

    public enum MetodoPago {
        EFECTIVO, TARJETA
    }

    public Long getId() { return id; }
    public Long getComandaId() { return comandaId; }
    public void setComandaId(Long comandaId) { this.comandaId = comandaId; }
    public Double getBaseTotal() { return baseTotal; }
    public void setBaseTotal(Double baseTotal) { this.baseTotal = baseTotal; }
    public Double getIvaTotal() { return ivaTotal; }
    public void setIvaTotal(Double ivaTotal) { this.ivaTotal = ivaTotal; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public LocalDateTime getCreadoAt() { return creadoAt; }
    public void setCreadoAt(LocalDateTime creadoAt) { this.creadoAt = creadoAt; }
    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
}

