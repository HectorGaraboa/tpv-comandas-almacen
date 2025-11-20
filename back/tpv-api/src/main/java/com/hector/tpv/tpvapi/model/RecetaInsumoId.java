package com.hector.tpv.tpvapi.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class RecetaInsumoId implements Serializable {
    private Long recetaId;
    private Long insumoId;

    public RecetaInsumoId() { }

    public RecetaInsumoId(Long recetaId, Long insumoId) {
        this.recetaId = recetaId;
        this.insumoId = insumoId;
    }

    public Long getRecetaId() { return recetaId; }
    public void setRecetaId(Long recetaId) { this.recetaId = recetaId; }

    public Long getInsumoId() { return insumoId; }
    public void setInsumoId(Long insumoId) { this.insumoId = insumoId; }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != getClass()) return false;
        RecetaInsumoId x = (RecetaInsumoId) o;
        boolean a = (recetaId == null && x.recetaId == null) || (recetaId != null && recetaId.equals(x.recetaId));
        boolean b = (insumoId == null && x.insumoId == null) || (insumoId != null && insumoId.equals(x.insumoId));
        return a && b;
    }

    @Override
    public int hashCode() {
        int r = (recetaId == null) ? 0 : recetaId.hashCode();
        int i = (insumoId == null) ? 0 : insumoId.hashCode();
        return 31 * r + i;
    }
}
