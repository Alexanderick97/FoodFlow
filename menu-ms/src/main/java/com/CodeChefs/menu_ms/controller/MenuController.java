package com.CodeChefs.menu_ms.controller;

import com.CodeChefs.menu_ms.dto.MenuRequestDTO;
import com.CodeChefs.menu_ms.dto.MenuResponseDTO;
import com.CodeChefs.menu_ms.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ResponseEntity<List<MenuResponseDTO>> listarPlatos() {
        return ResponseEntity.ok(menuService.listarPlatos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        MenuResponseDTO plato = menuService.buscarPorId(id);
        if (plato == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Plato no encontrado con id: " + id);
        }
        return ResponseEntity.ok(plato);
    }

    @PostMapping
    public ResponseEntity<?> crearPlato(@Valid @RequestBody MenuRequestDTO dto) {
        try {
            MenuResponseDTO nuevo = menuService.crearPlato(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPlato(@PathVariable int id, @Valid @RequestBody MenuRequestDTO dto) {
        try {
            MenuResponseDTO actualizado = menuService.actualizarPlato(id, dto);
            if (actualizado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Plato no encontrado con id: " + id);
            }
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/disponibilidad")
    public ResponseEntity<?> actualizarDisponibilidad(@PathVariable int id, @RequestParam boolean disponible) {
        MenuResponseDTO actualizado = menuService.actualizarDisponibilidad(id, disponible);
        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Plato no encontrado con id: " + id);
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPlato(@PathVariable int id) {
        if (menuService.eliminarPlato(id)) {
            return ResponseEntity.ok("Plato eliminado correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Plato no encontrado con id: " + id);
    }

    // Consultas derivadas
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<MenuResponseDTO>> buscarPorRestaurante(@PathVariable int restauranteId) {
        return ResponseEntity.ok(menuService.buscarPorRestaurante(restauranteId));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<MenuResponseDTO>> buscarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(menuService.buscarPorCategoria(categoria));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<MenuResponseDTO>> listarDisponibles() {
        return ResponseEntity.ok(menuService.listarDisponibles());
    }

    @GetMapping("/restaurante/{restauranteId}/disponibles")
    public ResponseEntity<List<MenuResponseDTO>> buscarMenuDisponiblePorRestaurante(@PathVariable int restauranteId) {
        return ResponseEntity.ok(menuService.buscarMenuDisponiblePorRestaurante(restauranteId));
    }

    @GetMapping("/precio")
    public ResponseEntity<List<MenuResponseDTO>> buscarPorRangoPrecio(
            @RequestParam double min,
            @RequestParam double max) {
        return ResponseEntity.ok(menuService.buscarPorRangoPrecio(min, max));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<MenuResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(menuService.buscarPorNombre(nombre));
    }
}