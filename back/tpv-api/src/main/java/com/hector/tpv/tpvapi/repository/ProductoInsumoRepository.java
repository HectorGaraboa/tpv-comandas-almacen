package com.hector.tpv.tpvapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hector.tpv.tpvapi.model.ProductoInsumo;
import com.hector.tpv.tpvapi.model.ProductoInsumoId;

public interface ProductoInsumoRepository extends JpaRepository<ProductoInsumo, ProductoInsumoId> {
    List<ProductoInsumo> findByProducto_Id(Long productoId);
}
