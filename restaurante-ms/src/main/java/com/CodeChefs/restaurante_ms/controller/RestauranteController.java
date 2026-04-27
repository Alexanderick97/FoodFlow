package com.CodeChefs.restaurante_ms.controller;

import com.CodeChefs.restaurante_ms.dto.RestauranteRequestDTO;
import com.CodeChefs.restaurante_ms.dto.RestauranteResponseDTO;
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
    public ResponseEntity<List<RestauranteResponseDTO>> listarRestaurantes() {
        return ResponseEntity.ok(restauranteService.listarRestaurantes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        RestauranteResponseDTO restaurante = restauranteService.buscarPorId(id);
        if (restaurante == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Restaurante no encontrado con id: " + id);
        }
        return ResponseEntity.ok(restaurante);
    }

    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> crearRestaurante(@Valid @RequestBody RestauranteRequestDTO dto) {
        RestauranteResponseDTO nuevo = restauranteService.crearRestaurante(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRestaurante(@PathVariable int id, @Valid @RequestBody RestauranteRequestDTO dto) {
        RestauranteResponseDTO actualizado = restauranteService.actualizarRestaurante(id, dto);
        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Restaurante no encontrado con id: " + id);
        }
        return ResponseEntity.ok(actualizado);
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
    public ResponseEntity<List<RestauranteResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(restauranteService.buscarPorNombre(nombre));
    }

    @GetMapping("/activos")
    public ResponseEntity<List<RestauranteResponseDTO>> listarActivos() {
        return ResponseEntity.ok(restauranteService.listarActivos());
    }

    @GetMapping("/calificacion/{minima}")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarPorCalificacion(@PathVariable double minima) {
        return ResponseEntity.ok(restauranteService.buscarPorCalificacion(minima));
    }
}