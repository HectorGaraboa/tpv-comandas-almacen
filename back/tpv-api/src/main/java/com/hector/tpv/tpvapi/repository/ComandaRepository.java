package com.hector.tpv.tpvapi.repository;

import com.hector.tpv.tpvapi.model.Comanda;
import com.hector.tpv.tpvapi.model.Comanda.Estado;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComandaRepository extends JpaRepository<Comanda, Long> {

    List<Comanda> findByMesaIdAndEstadoIn(Long mesaId, java.util.List<Comanda.Estado> estados);

    List<Comanda> findByEstado(Estado estado);

    List<Comanda> findByEstadoAndCobradaAtAfter(Estado estado, LocalDateTime fecha);

}
