package com.hector.tpv.tpvapi.repository;

import com.hector.tpv.tpvapi.model.CierreTurno;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CierreTurnoRepository extends JpaRepository<CierreTurno, Long> {

    Optional<CierreTurno> findTopByOrderByCreadoAtDesc();
}
