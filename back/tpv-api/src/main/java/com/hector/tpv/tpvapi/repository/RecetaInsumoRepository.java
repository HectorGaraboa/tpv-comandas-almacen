package com.hector.tpv.tpvapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hector.tpv.tpvapi.model.RecetaInsumo;
import com.hector.tpv.tpvapi.model.RecetaInsumoId;

public interface RecetaInsumoRepository extends JpaRepository<RecetaInsumo, RecetaInsumoId> {
    List<RecetaInsumo> findByReceta_Id(Long recetaId);
}
