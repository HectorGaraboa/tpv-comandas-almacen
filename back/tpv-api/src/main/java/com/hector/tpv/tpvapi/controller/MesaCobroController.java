package com.hector.tpv.tpvapi.controller;

import com.hector.tpv.tpvapi.dto.CobroMesaRequest;
import com.hector.tpv.tpvapi.dto.TicketDTO;
import com.hector.tpv.tpvapi.model.Ticket;
import com.hector.tpv.tpvapi.service.ComandaService;
import com.hector.tpv.tpvapi.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mesas")
public class MesaCobroController {

    private static final Logger log = LoggerFactory.getLogger(MesaCobroController.class);

    private final ComandaService comandaService;
    private final ReportService reportService;

    public MesaCobroController(ComandaService comandaService, ReportService reportService) {
        this.comandaService = comandaService;
        this.reportService = reportService;
    }

    @PostMapping("/{mesaId}/cobrar")
    public ResponseEntity<TicketDTO> cobrarMesa(@PathVariable Long mesaId, @RequestBody CobroMesaRequest req) {

        Ticket.MetodoPago mp = req == null || req.getMetodoPago() == null
                ? Ticket.MetodoPago.EFECTIVO
                : Ticket.MetodoPago.valueOf(req.getMetodoPago().toUpperCase());

        Ticket t = comandaService.cobrarMesa(mesaId, mp);

        try {
            String path = reportService.generarTicketPdf(t.getId());
            log.info("Generado ticket PDF {} para ticket {}", path, t.getId());
        } catch (Exception e) {
            log.error("Error generando PDF de ticket " + t.getId(), e);
        }

        return ResponseEntity.ok(TicketDTO.fromEntity(t));
    }
}
