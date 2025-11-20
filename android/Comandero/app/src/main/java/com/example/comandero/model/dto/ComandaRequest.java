package com.example.comandero.model.dto;

import java.util.List;

public class ComandaRequest {
    private Long mesaId;
    private Long camareroId;
    private List<Linea> lineas;

    public static class Linea {
        private Long productoId;
        private Integer cantidad;
        private String textoModificador;
        private Double precioUnitario;
        private Double ivaTipo;

        public Linea(Long productoId, Integer cantidad, String textoModificador, Double precioUnitario, Double ivaTipo) {
            this.productoId = productoId;
            this.cantidad = cantidad;
            this.textoModificador = textoModificador;
            this.precioUnitario = precioUnitario;
            this.ivaTipo = ivaTipo;
        }

        public Long getProductoId() { return productoId; }
        public Integer getCantidad() { return cantidad; }
        public String getTextoModificador() { return textoModificador; }
        public Double getPrecioUnitario() { return precioUnitario; }
        public Double getIvaTipo() { return ivaTipo; }
    }

    public static class ComandaRespuesta {
        public Long id;
        public Long getId() { return id; }
    }

    public Long getMesaId() { return mesaId; }
    public Long getCamareroId() { return camareroId; }
    public List<Linea> getLineas() { return lineas; }

    public void setMesaId(Long mesaId) { this.mesaId = mesaId; }
    public void setCamareroId(Long camareroId) { this.camareroId = camareroId; }
    public void setLineas(List<Linea> lineas) { this.lineas = lineas; }
}
