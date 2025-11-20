
package com.hector.tpv.tpvapi.model;



import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movimiento")
public class StockMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "insumo_id")
    private Long insumoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoMovimiento tipo;

    public enum TipoMovimiento {
        ENTRADA, SALIDA, AJUSTE
    }

    @Column(name = "cantidad_base")
    private Double cantidadBase;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "ref_comanda_id")
    private Long refComandaId;

    @Column(name = "ref_linea_id")
    private Long refLineaId;

    @Column(name = "creado_at")
    private LocalDateTime creadoAt;

    public Long getId() { return id; }
    public Long getInsumoId() { return insumoId; }
    public void setInsumoId(Long insumoId) { this.insumoId = insumoId; }
    public TipoMovimiento getTipo() { return tipo; }
    public void setTipo(TipoMovimiento tipo) { this.tipo = tipo; }
    public Double getCantidadBase() { return cantidadBase; }
    public void setCantidadBase(Double cantidadBase) { this.cantidadBase = cantidadBase; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public Long getRefComandaId() { return refComandaId; }
    public void setRefComandaId(Long refComandaId) { this.refComandaId = refComandaId; }
    public Long getRefLineaId() { return refLineaId; }
    public void setRefLineaId(Long refLineaId) { this.refLineaId = refLineaId; }
    public LocalDateTime getCreadoAt() { return creadoAt; }
    public void setCreadoAt(LocalDateTime creadoAt) { this.creadoAt = creadoAt; }
}

