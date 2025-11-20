package com.hector.tpv.tpvapi.dto;

import java.math.BigDecimal;
import java.util.List;

public class NuevaComandaRequest {

    private Long mesaId;
    private Long camareroId;
    private List<Linea> lineas;

    public static class Linea {

        private Long productoId;
        private Integer cantidad;
        private String textoModificador;
        private BigDecimal precioUnitario;
        private BigDecimal ivaTipo;

        public Long getProductoId() {
            return productoId;
        }

        public void setProductoId(Long productoId) {
            this.productoId = productoId;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public String getTextoModificador() {
            return textoModificador;
        }

        public void setTextoModificador(String textoModificador) {
            this.textoModificador = textoModificador;
        }

        public BigDecimal getPrecioUnitario() {
            return precioUnitario;
        }

        public void setPrecioUnitario(BigDecimal precioUnitario) {
            this.precioUnitario = precioUnitario;
        }

        public BigDecimal getIvaTipo() {
            return ivaTipo;
        }

        public void setIvaTipo(BigDecimal ivaTipo) {
            this.ivaTipo = ivaTipo;
        }
    }

    public Long getMesaId() {
        return mesaId;
    }

    public void setMesaId(Long mesaId) {
        this.mesaId = mesaId;
    }

    public Long getCamareroId() {
        return camareroId;
    }

    public void setCamareroId(Long camareroId) {
        this.camareroId = camareroId;
    }

    public List<Linea> getLineas() {
        return lineas;
    }

    public void setLineas(List<Linea> lineas) {
        this.lineas = lineas;
    }
}
