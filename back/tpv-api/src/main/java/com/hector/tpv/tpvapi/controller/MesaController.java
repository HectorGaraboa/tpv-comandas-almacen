package com.hector.tpv.tpvapi.controller;

import com.hector.tpv.tpvapi.dto.MesaDTO;
import com.hector.tpv.tpvapi.repository.ComandaLineaAggDao;

import com.hector.tpv.tpvapi.repository.MesaRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@CrossOrigin
public class MesaController {

    private final MesaRepository repo;
    private final ComandaLineaAggDao aggDao;

    public MesaController(MesaRepository repo, ComandaLineaAggDao aggDao) {
        this.aggDao = aggDao;
        this.repo = repo;
    }

    @GetMapping
    public List<MesaDTO> listar() {
        return repo.findAll().stream()
                .map(m -> {
                    Long zonaId = m.getZona() != null ? m.getZona().getId() : null;

                    boolean ocupada = !aggDao.aggByMesa(m.getId()).isEmpty();

                    return new MesaDTO(
                            m.getId(),
                            m.getCodigo(),
                            zonaId,
                            ocupada
                    );
                }).toList();
    }
}
