package com.hector.tpv.tpvapi.controller;

import com.hector.tpv.tpvapi.dto.LoginRequest;
import com.hector.tpv.tpvapi.dto.UsuarioSesionDTO;
import com.hector.tpv.tpvapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioSesionDTO> login(@RequestBody LoginRequest req) {
        try {
            UsuarioSesionDTO dto = authService.login(req.getUsuario(), req.getPassword());
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
