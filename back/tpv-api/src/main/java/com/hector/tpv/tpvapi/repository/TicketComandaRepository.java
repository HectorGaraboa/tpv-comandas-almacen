package com.hector.tpv.tpvapi.repository;

import com.hector.tpv.tpvapi.model.TicketComanda;
import com.hector.tpv.tpvapi.model.TicketComanda.TicketComandaId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketComandaRepository extends JpaRepository<TicketComanda, TicketComandaId> {
    List<TicketComanda> findByIdComandaIdIn(List<Long> comandaIds);
}
