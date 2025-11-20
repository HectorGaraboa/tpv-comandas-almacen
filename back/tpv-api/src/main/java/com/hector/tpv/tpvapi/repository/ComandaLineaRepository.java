package com.hector.tpv.tpvapi.repository;

import com.hector.tpv.tpvapi.model.ComandaLinea;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComandaLineaRepository extends JpaRepository<ComandaLinea, Long> {

    List<ComandaLinea> findByComandaIdAndAnulada(Long comandaId, Integer anulada);
}
