package com.hector.tpv.tpvapi.repository;

import com.hector.tpv.tpvapi.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
