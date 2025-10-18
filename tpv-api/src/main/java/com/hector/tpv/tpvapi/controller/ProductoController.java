package com.hector.tpv.tpvapi.controller;

import com.hector.tpv.tpvapi.model.Producto;
import com.hector.tpv.tpvapi.repository.ProductoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoRepository repo;
    public ProductoController(ProductoRepository repo) { this.repo = repo; }
    @GetMapping
    public List<Producto> listar() {
        return repo.findAll();
    }
}
