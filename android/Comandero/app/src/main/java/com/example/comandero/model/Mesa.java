package com.example.comandero.model;

public class Mesa {
    private Long id;
    private String codigo;
    private Long zonaId;
    private boolean ocupada;

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public Long getZonaId() { return zonaId; }
    public boolean isOcupada() { return ocupada; }

    public void setId(Long id) { this.id = id; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setZonaId(Long zonaId) { this.zonaId = zonaId; }
    public void setOcupada(boolean ocupada) { this.ocupada = ocupada; }
}
