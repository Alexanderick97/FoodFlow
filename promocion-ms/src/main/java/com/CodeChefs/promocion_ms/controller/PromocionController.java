package com.CodeChefs.PromocionMS.controller;

import com.CodeChefs.PromocionMS.model.Promocion;
import com.CodeChefs.PromocionMS.service.PromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promociones")
public class PromocionController {

    @Autowired
    private PromocionService promocionService;


    @GetMapping
    public List<Promocion> obtenerTodas() {
        return promocionService.obtenerTodas();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Promocion> obtenerPorId(@PathVariable Long id) {
        return promocionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public Promocion crearPromocion(@RequestBody Promocion promocion) {
        return promocionService.guardarPromocion(promocion);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Promocion> actualizarPromocion(
            @PathVariable Long id,
            @RequestBody Promocion promocion) {

        try {
            Promocion actualizada = promocionService.actualizarPromocion(id, promocion);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPromocion(@PathVariable Long id) {
        promocionService.eliminarPromocion(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/activas")
    public List<Promocion> obtenerPromocionesActivas() {
        return promocionService.obtenerPromocionesActivas();
    }

    @GetMapping("/producto/{productoId}")
    public List<Promocion> obtenerPorProducto(@PathVariable Long productoId) {
        return promocionService.obtenerPorProducto(productoId);
    }
}