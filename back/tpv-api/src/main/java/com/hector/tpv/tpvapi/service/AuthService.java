package com.hector.tpv.tpvapi.service;

import com.hector.tpv.tpvapi.dto.UsuarioSesionDTO;
import com.hector.tpv.tpvapi.model.Usuario;
import com.hector.tpv.tpvapi.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioSesionDTO login(String nombre, String pinPlano) {
        Usuario u = usuarioRepository
                .findByNombreAndActivoTrue(nombre)
                .orElseThrow(() -> new IllegalArgumentException("Usuario o PIN incorrectos"));

        String guardado = u.getPinHash();
        boolean ok;

        if (guardado != null && guardado.startsWith("$2a$")) {
            ok = encoder.matches(pinPlano, guardado);
        } else {
            ok = pinPlano.equals(guardado);
        }

        if (!ok) {
            throw new IllegalArgumentException("Usuario o PIN incorrectos");
        }

        String rol = usuarioRepository.findRolNombreByUsuarioId(u.getId());
        if (rol == null) {
            rol = "CAMARERO";
        }

        UsuarioSesionDTO dto = new UsuarioSesionDTO();
        dto.setId(u.getId());
        dto.setNombre(u.getNombre());
        dto.setRol(rol);
        return dto;
    }
}
