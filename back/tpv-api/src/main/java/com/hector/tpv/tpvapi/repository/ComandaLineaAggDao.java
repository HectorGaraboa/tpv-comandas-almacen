package com.hector.tpv.tpvapi.repository;

import com.hector.tpv.tpvapi.dto.MesaAbiertaItemDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ComandaLineaAggDao {

    @PersistenceContext
    private EntityManager em;

    public List<MesaAbiertaItemDTO> aggByMesa(Long mesaId) {
        String sql = """
            SELECT cl.producto_id AS productoId,
                   p.nombre        AS nombre,
                   SUM(cl.cantidad)                           AS cantidad,
                   AVG(cl.precio_unitario)                    AS precioUnitario,
                   AVG(cl.iva_tipo)                           AS ivaTipo,
                   SUM(cl.cantidad * cl.precio_unitario)      AS subtotal
            FROM comanda c
            JOIN comanda_linea cl ON cl.comanda_id = c.id AND cl.anulada = 0
            JOIN producto p       ON p.id = cl.producto_id
            WHERE c.mesa_id = :mesaId
              AND c.estado IN ('ABIERTA','ENVIADA')
            GROUP BY cl.producto_id, p.nombre
            ORDER BY p.nombre
        """;

        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(sql)
                .setParameter("mesaId", mesaId)
                .getResultList();

        return rows.stream().map(r -> new MesaAbiertaItemDTO(
                ((Number) r[0]).longValue(),
                (String) r[1],
                ((Number) r[2]).longValue(),
                ((Number) r[3]).doubleValue(),
                ((Number) r[4]).doubleValue(),
                ((Number) r[5]).doubleValue()
        )).toList();
    }
}
