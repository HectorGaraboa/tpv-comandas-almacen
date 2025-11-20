package com.hector.tpv.tpvapi.service;

import com.hector.tpv.tpvapi.model.Comanda;
import com.hector.tpv.tpvapi.model.Ticket;
import com.hector.tpv.tpvapi.model.TicketComanda;
import com.hector.tpv.tpvapi.repository.ComandaRepository;
import com.hector.tpv.tpvapi.repository.ComandaLineaRepository;
import com.hector.tpv.tpvapi.repository.TicketComandaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class ComandaService {

    private final ComandaRepository comandaRepository;
    private final ComandaLineaRepository comandaLineaRepository;
    private final TicketService ticketService;
    private final StockService stockService;
    private final TicketComandaRepository ticketComandaRepo;

    public ComandaService(ComandaRepository comandaRepository,
            ComandaLineaRepository comandaLineaRepository,
            TicketService ticketService,
            StockService stockService, TicketComandaRepository ticketComandaRepo) {
        this.comandaRepository = comandaRepository;
        this.comandaLineaRepository = comandaLineaRepository;
        this.ticketService = ticketService;
        this.stockService = stockService;
        this.ticketComandaRepo = ticketComandaRepo;
    }

    

    @Transactional
public Ticket cobrarMesa(Long mesaId, Ticket.MetodoPago metodoPago) {
    List<Comanda.Estado> estados = Arrays.asList(
            Comanda.Estado.ABIERTA,
            Comanda.Estado.ENVIADA
    );
    List<Comanda> comandas = comandaRepository.findByMesaIdAndEstadoIn(mesaId, estados);
    if (comandas.isEmpty()) {
        throw new IllegalStateException("Sin comandas abiertas para la mesa");
    }
    List<Long> ids = comandas.stream().map(Comanda::getId).toList();

    Ticket ticket = ticketService.generarTicketMultiple(ids, metodoPago);

    for (Long id : ids) {
        stockService.descontarPorComanda(id);
    }
    for (Comanda c : comandas) {
        c.setEstado(Comanda.Estado.COBRADA);
        c.setCobradaAt(LocalDateTime.now());
        comandaRepository.save(c);
    }
    for (Long id : ids) {
        ticketComandaRepo.save(new TicketComanda(ticket.getId(), id));
    }
    return ticket;
}


}
