package com.CodeChefs.restaurante_ms.controller;

import com.CodeChefs.restaurante_ms.model.Restaurante;
import com.CodeChefs.restaurante_ms.service.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @GetMapping
    public ResponseEntity<List<Restaurante>> listarRestaurantes() {
        return ResponseEntity.ok(restauranteService.listarRestaurantes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        return restauranteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Restaurante no encontrado con id: " + id));
    }

    @PostMapping
    public ResponseEntity<?> crearRestaurante(@Valid @RequestBody Restaurante restaurante) {
        Restaurante nuevo = restauranteService.crearRestaurante(restaurante);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRestaurante(@PathVariable int id, @RequestBody Restaurante restaurante) {
        return restauranteService.actualizarRestaurante(id, restaurante)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Restaurante no encontrado con id: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRestaurante(@PathVariable int id) {
        if (restauranteService.eliminarRestaurante(id)) {
            return ResponseEntity.ok("Restaurante eliminado correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Restaurante no encontrado con id: " + id);
    }

    // Consultas derivadas
    @GetMapping("/buscar")
    public ResponseEntity<List<Restaurante>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(restauranteService.buscarPorNombre(nombre));
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Restaurante>> listarActivos() {
        return ResponseEntity.ok(restauranteService.listarActivos());
    }

    @GetMapping("/calificacion/{minima}")
    public ResponseEntity<List<Restaurante>> buscarPorCalificacion(@PathVariable double minima) {
        return ResponseEntity.ok(restauranteService.buscarPorCalificacion(minima));
    }
}