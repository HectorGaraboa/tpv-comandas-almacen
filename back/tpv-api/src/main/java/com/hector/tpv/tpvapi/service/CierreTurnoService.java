package com.hector.tpv.tpvapi.service;

import com.hector.tpv.tpvapi.model.CierreTurno;
import com.hector.tpv.tpvapi.model.Comanda;
import com.hector.tpv.tpvapi.model.Ticket;
import com.hector.tpv.tpvapi.model.TicketComanda;
import com.hector.tpv.tpvapi.repository.CierreTurnoRepository;
import com.hector.tpv.tpvapi.repository.ComandaRepository;
import com.hector.tpv.tpvapi.repository.TicketComandaRepository;
import com.hector.tpv.tpvapi.repository.TicketRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CierreTurnoService {

    private final CierreTurnoRepository cierreRepo;
    private final ComandaRepository comandaRepo;
    private final TicketComandaRepository ticketComandaRepo;
    private final TicketRepository ticketRepo;

    public CierreTurnoService(CierreTurnoRepository cierreRepo,
            ComandaRepository comandaRepo,
            TicketComandaRepository ticketComandaRepo,
            TicketRepository ticketRepo) {
        this.cierreRepo = cierreRepo;
        this.comandaRepo = comandaRepo;
        this.ticketComandaRepo = ticketComandaRepo;
        this.ticketRepo = ticketRepo;
    }

    @Transactional
    public CierreTurno cerrarTurno() {
        LocalDateTime ahora = LocalDateTime.now();

        Optional<CierreTurno> ultimoOpt = cierreRepo.findTopByOrderByCreadoAtDesc();
        LocalDateTime ultimoCierre = null;
        if (ultimoOpt.isPresent()) {
            ultimoCierre = ultimoOpt.get().getCreadoAt();
        }

        List<Comanda> comandas = new ArrayList<>();
        if (ultimoCierre != null) {
            comandas = comandaRepo.findByEstadoAndCobradaAtAfter(Comanda.Estado.COBRADA, ultimoCierre);
        } else {
            comandas = comandaRepo.findByEstado(Comanda.Estado.COBRADA);
        }

        if (comandas.isEmpty()) {
            throw new IllegalStateException("No hay comandas cobradas para cerrar");
        }

        List<Long> comandaIds = new ArrayList<>();
        for (Comanda c : comandas) {
            comandaIds.add(c.getId());
        }

        List<TicketComanda> relaciones = ticketComandaRepo.findByIdComandaIdIn(comandaIds);
        Set<Long> ticketIds = new HashSet<>();
        for (TicketComanda tc : relaciones) {
            ticketIds.add(tc.getId().getTicketId());
        }

        List<Ticket> tickets = ticketRepo.findAllById(ticketIds);

        BigDecimal base = BigDecimal.ZERO;
        BigDecimal iva = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;

        for (Ticket t : tickets) {
            if (t.getBaseTotal() != null) {
                base = base.add(BigDecimal.valueOf(t.getBaseTotal()));
            }
            if (t.getIvaTotal() != null) {
                iva = iva.add(BigDecimal.valueOf(t.getIvaTotal()));
            }
            if (t.getTotal() != null) {
                total = total.add(BigDecimal.valueOf(t.getTotal()));
            }
        }

        LocalDateTime desde = null;
        LocalDateTime hasta = null;

        for (Comanda c : comandas) {
            if (c.getCobradaAt() != null) {
                if (desde == null || c.getCobradaAt().isBefore(desde)) {
                    desde = c.getCobradaAt();
                }
                if (hasta == null || c.getCobradaAt().isAfter(hasta)) {
                    hasta = c.getCobradaAt();
                }
            }
        }

        if (desde == null) {
            desde = ahora;
        }
        if (hasta == null) {
            hasta = ahora;
        }

        CierreTurno cierre = new CierreTurno();
        cierre.setCreadoAt(ahora);
        cierre.setDesde(desde);
        cierre.setHasta(hasta);
        cierre.setNumComandas(comandas.size());
        cierre.setBaseTotal(base.doubleValue());
        cierre.setIvaTotal(iva.doubleValue());
        cierre.setTotal(total.doubleValue());

        cierreRepo.save(cierre);

        for (Comanda c : comandas) {
            c.setEstado(Comanda.Estado.CERRADA);
            c.setCerradaAt(ahora);
            comandaRepo.save(c);
        }

        return cierre;
    }
}
