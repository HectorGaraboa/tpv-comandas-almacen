package com.hector.tpv.tpvapi.controller;

import com.hector.tpv.tpvapi.model.Categoria;
import com.hector.tpv.tpvapi.repository.CategoriaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin
public class CategoriaController {

    private final CategoriaRepository categorias;

    public CategoriaController(CategoriaRepository categorias) {
        this.categorias = categorias;
    }

    @GetMapping
    public List<Categoria> listar() {
        return categorias.findAll();
    }
}
