package com.hector.tpv.tpvapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comanda")
public class Comanda {
    public enum Estado { ABIERTA, ENVIADA, ANULADA, COBRADA, CERRADA }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="mesa_id")
    private Long mesaId;

    @Column(name="camarero_id")
    private Long camareroId;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Column(name="creada_at")
    private LocalDateTime creadaAt;

    @Column(name="enviada_at")
    private LocalDateTime enviadaAt;

    @Column(name="cobrada_at")
    private LocalDateTime cobradaAt;

    @Column(name="cerrada_at")
    private LocalDateTime cerradaAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getMesaId() { return mesaId; }
    public void setMesaId(Long mesaId) { this.mesaId = mesaId; }
    public Long getCamareroId() { return camareroId; }
    public void setCamareroId(Long camareroId) { this.camareroId = camareroId; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public LocalDateTime getCreadaAt() { return creadaAt; }
    public void setCreadaAt(LocalDateTime creadaAt) { this.creadaAt = creadaAt; }
    public LocalDateTime getEnviadaAt() { return enviadaAt; }
    public void setEnviadaAt(LocalDateTime enviadaAt) { this.enviadaAt = enviadaAt; }
    public LocalDateTime getCobradaAt() { return cobradaAt; }
    public void setCobradaAt(LocalDateTime cobradaAt) { this.cobradaAt = cobradaAt; }
    public LocalDateTime getCerradaAt() { return cerradaAt; }
    public void setCerradaAt(LocalDateTime cerradaAt) { this.cerradaAt = cerradaAt; }
}
