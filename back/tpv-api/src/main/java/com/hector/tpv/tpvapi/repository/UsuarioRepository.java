package com.hector.tpv.tpvapi.repository;

import com.hector.tpv.tpvapi.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNombreAndActivoTrue(String nombre);

    @Query(
        value = "select r.nombre " +
                "from rol r " +
                "join usuario_rol ur on r.id = ur.rol_id " +
                "where ur.usuario_id = :usuarioId " +
                "limit 1",
        nativeQuery = true
    )
    String findRolNombreByUsuarioId(@Param("usuarioId") Long usuarioId);
}
