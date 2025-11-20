package com.hector.tpv.tpvapi.repository;

import com.hector.tpv.tpvapi.model.Receta;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    Optional<Receta> findByProductoId(Long productoId);
}
