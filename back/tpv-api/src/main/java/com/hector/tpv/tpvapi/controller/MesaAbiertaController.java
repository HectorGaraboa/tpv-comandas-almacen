package com.hector.tpv.tpvapi.controller;

import com.hector.tpv.tpvapi.model.Mesa;
import com.hector.tpv.tpvapi.repository.ComandaLineaAggDao;
import com.hector.tpv.tpvapi.repository.MesaRepository;
import com.hector.tpv.tpvapi.dto.MesaAbiertaDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mesas")
@CrossOrigin
public class MesaAbiertaController {

    private final MesaRepository mesaRepo;
    private final ComandaLineaAggDao aggDao;

    public MesaAbiertaController(MesaRepository mesaRepo, ComandaLineaAggDao aggDao) {
        this.mesaRepo = mesaRepo;
        this.aggDao = aggDao;
    }

    @GetMapping("/{mesaId}/abierto")
    public MesaAbiertaDTO getAbierto(@PathVariable Long mesaId) {
        Mesa m = mesaRepo.findById(mesaId).orElseThrow();
        var items = aggDao.aggByMesa(mesaId);
        double total = items.stream().mapToDouble(i -> i.getSubtotal()).sum();
        return new MesaAbiertaDTO(m.getId(), m.getCodigo(), total, items);
    }
}
