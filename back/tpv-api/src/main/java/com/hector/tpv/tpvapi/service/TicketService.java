package com.hector.tpv.tpvapi.service;

import com.hector.tpv.tpvapi.model.ComandaLinea;
import com.hector.tpv.tpvapi.model.Ticket;
import com.hector.tpv.tpvapi.repository.ComandaLineaRepository;
import com.hector.tpv.tpvapi.repository.ComandaRepository;
import com.hector.tpv.tpvapi.repository.TicketRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketRepository ticketRepo;
    private final ComandaRepository comandaRepo;
    private final ComandaLineaRepository lineaRepo;

    public TicketService(TicketRepository ticketRepo,
                         ComandaRepository comandaRepo,
                         ComandaLineaRepository lineaRepo) {
        this.ticketRepo = ticketRepo;
        this.comandaRepo = comandaRepo;
        this.lineaRepo = lineaRepo;
    }

    
    public Ticket generarTicketMultiple(List<Long> comandaIds, Ticket.MetodoPago metodoPago) {
    BigDecimal base = BigDecimal.ZERO;
    BigDecimal iva = BigDecimal.ZERO;
    BigDecimal total = BigDecimal.ZERO;

    for (Long id : comandaIds) {
        List<ComandaLinea> lineas = lineaRepo.findByComandaIdAndAnulada(id, 0);
        for (ComandaLinea l : lineas) {
            BigDecimal cantidad = BigDecimal.valueOf(l.getCantidad());
            BigDecimal lineaTotal = l.getPrecioUnitario().multiply(cantidad);

            BigDecimal divisor = BigDecimal.ONE.add(
                    l.getIvaTipo().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
            );

            BigDecimal baseLinea = lineaTotal.divide(divisor, 2, RoundingMode.HALF_UP);
            BigDecimal ivaLinea = lineaTotal.subtract(baseLinea);

            base = base.add(baseLinea);
            iva = iva.add(ivaLinea);
            total = total.add(lineaTotal);
        }
    }

    Ticket t = new Ticket();
    t.setComandaId(null);
    t.setBaseTotal(base.doubleValue());
    t.setIvaTotal(iva.doubleValue());
    t.setTotal(total.doubleValue());
    t.setCreadoAt(LocalDateTime.now());
    t.setMetodoPago(metodoPago);

    return ticketRepo.save(t);
}



}
