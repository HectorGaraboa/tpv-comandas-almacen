package com.hector.tpv.tpvapi.service;

import com.hector.tpv.tpvapi.model.ComandaLinea;
import com.hector.tpv.tpvapi.model.Insumo;
import com.hector.tpv.tpvapi.model.ProductoInsumo;
import com.hector.tpv.tpvapi.model.RecetaInsumo;
import com.hector.tpv.tpvapi.model.StockMovimiento;
import com.hector.tpv.tpvapi.repository.ComandaLineaRepository;
import com.hector.tpv.tpvapi.repository.InsumoRepository;
import com.hector.tpv.tpvapi.repository.ProductoInsumoRepository;
import com.hector.tpv.tpvapi.repository.RecetaInsumoRepository;
import com.hector.tpv.tpvapi.repository.RecetaRepository;
import com.hector.tpv.tpvapi.repository.StockMovimientoRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private final ComandaLineaRepository lineaRepo;
    private final RecetaRepository recetaRepo;
    private final RecetaInsumoRepository recetaInsumoRepo;
    private final ProductoInsumoRepository productoInsumoRepo;
    private final StockMovimientoRepository stockRepo;
    private final InsumoRepository insumoRepo;

    public StockService(ComandaLineaRepository lineaRepo,
            RecetaRepository recetaRepo,
            RecetaInsumoRepository recetaInsumoRepo,
            ProductoInsumoRepository productoInsumoRepo,
            StockMovimientoRepository stockRepo,
            InsumoRepository insumoRepo) {
        this.lineaRepo = lineaRepo;
        this.recetaRepo = recetaRepo;
        this.recetaInsumoRepo = recetaInsumoRepo;
        this.productoInsumoRepo = productoInsumoRepo;
        this.stockRepo = stockRepo;
        this.insumoRepo = insumoRepo;
    }

    @Transactional
    public void descontarPorComanda(Long comandaId) {
        List<ComandaLinea> lineas = lineaRepo.findByComandaIdAndAnulada(comandaId, 0);
        for (ComandaLinea l : lineas) {
            Long productoId = l.getProductoId();
            int cantidad = l.getCantidad();

            var recetaOpt = recetaRepo.findByProductoId(productoId);
            if (recetaOpt.isPresent()) {
                Long recetaId = recetaOpt.get().getId();
                List<RecetaInsumo> insumos = recetaInsumoRepo.findByReceta_Id(recetaId);
                for (RecetaInsumo ri : insumos) {
                    BigDecimal total = ri.getCantidadBase().multiply(BigDecimal.valueOf(cantidad));
                    descontarInsumo(ri.getInsumo().getId(), total, comandaId, l.getId(), "RECETA");
                }
                continue;
            }

            List<ProductoInsumo> pi = productoInsumoRepo.findByProducto_Id(productoId);
            for (ProductoInsumo x : pi) {
                BigDecimal total = x.getCantidadBasePorUd().multiply(BigDecimal.valueOf(cantidad));
                descontarInsumo(x.getInsumo().getId(), total, comandaId, l.getId(), "PRODUCTO");
            }
        }
    }

    private void descontarInsumo(Long insumoId,
            BigDecimal cantidad,
            Long comandaId,
            Long lineaId,
            String motivo) {
        Insumo ins = insumoRepo.findById(insumoId).orElseThrow();
        ins.setStockActualBase(ins.getStockActualBase().subtract(cantidad));
        insumoRepo.save(ins);

        StockMovimiento m = new StockMovimiento();
        m.setInsumoId(insumoId);
        m.setTipo(StockMovimiento.TipoMovimiento.SALIDA);
        m.setCantidadBase(cantidad.doubleValue());
        m.setMotivo(motivo);
        m.setRefComandaId(comandaId);
        m.setRefLineaId(lineaId);
        m.setCreadoAt(LocalDateTime.now());
        stockRepo.save(m);
    }
}
