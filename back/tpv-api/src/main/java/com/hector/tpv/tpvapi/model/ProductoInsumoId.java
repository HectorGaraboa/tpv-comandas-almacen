package com.hector.tpv.tpvapi.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class ProductoInsumoId implements Serializable {
    private Long productoId;
    private Long insumoId;

    public ProductoInsumoId() { }

    public ProductoInsumoId(Long productoId, Long insumoId) {
        this.productoId = productoId;
        this.insumoId = insumoId;
    }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Long getInsumoId() { return insumoId; }
    public void setInsumoId(Long insumoId) { this.insumoId = insumoId; }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != getClass()) return false;
        ProductoInsumoId x = (ProductoInsumoId) o;
        boolean p = (productoId == null && x.productoId == null) || (productoId != null && productoId.equals(x.productoId));
        boolean i = (insumoId == null && x.insumoId == null) || (insumoId != null && insumoId.equals(x.insumoId));
        return p && i;
    }

    @Override
    public int hashCode() {
        int a = (productoId == null) ? 0 : productoId.hashCode();
        int b = (insumoId == null) ? 0 : insumoId.hashCode();
        return 31 * a + b;
    }
}
