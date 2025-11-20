package com.hector.tpv.tpvapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket_comanda")
public class TicketComanda {

    @EmbeddedId
    private TicketComandaId id = new TicketComandaId();

    public TicketComanda() {}

    public TicketComanda(Long ticketId, Long comandaId) {
        this.id.setTicketId(ticketId);
        this.id.setComandaId(comandaId);
    }

    public TicketComandaId getId() { return id; }
    public void setId(TicketComandaId id) { this.id = id; }

    @Embeddable
    public static class TicketComandaId implements java.io.Serializable {
        @Column(name = "ticket_id")
        private Long ticketId;
        @Column(name = "comanda_id")
        private Long comandaId;
        public Long getTicketId() { return ticketId; }
        public void setTicketId(Long ticketId) { this.ticketId = ticketId; }
        public Long getComandaId() { return comandaId; }
        public void setComandaId(Long comandaId) { this.comandaId = comandaId; }
        public int hashCode() { return java.util.Objects.hash(ticketId, comandaId); }
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TicketComandaId that = (TicketComandaId) o;
            return java.util.Objects.equals(ticketId, that.ticketId) &&
                   java.util.Objects.equals(comandaId, that.comandaId);
        }
    }
}
