package com.hector.tpv.tpvapi.controller;

import com.hector.tpv.tpvapi.dto.NuevaComandaRequest;
import com.hector.tpv.tpvapi.model.Comanda;
import com.hector.tpv.tpvapi.model.ComandaLinea;
import com.hector.tpv.tpvapi.repository.ComandaLineaRepository;
import com.hector.tpv.tpvapi.repository.ComandaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/comandas")
public class ComandaController {

    private final ComandaRepository comandaRepo;
    private final ComandaLineaRepository lineaRepo;

    public ComandaController(ComandaRepository comandaRepo, ComandaLineaRepository lineaRepo) {
        this.comandaRepo = comandaRepo;
        this.lineaRepo = lineaRepo;
    }

    @PostMapping
    @Transactional
    public Comanda crear(@RequestBody NuevaComandaRequest req) {
        Comanda c = new Comanda();
        c.setMesaId(req.getMesaId());
        c.setCamareroId(req.getCamareroId());
        c.setEstado(Comanda.Estado.ABIERTA);
        c.setCreadaAt(LocalDateTime.now());
        c = comandaRepo.save(c);

        if (req.getLineas() != null) {
            List<ComandaLinea> toSave = new ArrayList<>();
            for (NuevaComandaRequest.Linea l : req.getLineas()) {
                ComandaLinea cl = new ComandaLinea();
                cl.setComandaId(c.getId());
                cl.setProductoId(l.getProductoId());
                cl.setCantidad(l.getCantidad());
                cl.setTextoModificador(l.getTextoModificador());
                cl.setAnulada(0);
                cl.setPrecioUnitario(l.getPrecioUnitario());
                cl.setIvaTipo(l.getIvaTipo());
                toSave.add(cl);
            }
            lineaRepo.saveAll(toSave);
        }
        return c;
    }
   

}
