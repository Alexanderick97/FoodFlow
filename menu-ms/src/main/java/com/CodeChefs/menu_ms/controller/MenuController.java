package com.CodeChefs.menu_ms.controller;

import com.CodeChefs.menu_ms.dto.MenuRequestDTO;
import com.CodeChefs.menu_ms.dto.MenuResponseDTO;
import com.CodeChefs.menu_ms.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "Menú", description = "Endpoints para gestionar platos del menú")
@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @Operation(summary = "Listar platos", description = "Obtiene todos los platos registrados")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<MenuResponseDTO>> listarPlatos() {
        return ResponseEntity.ok(menuService.listarPlatos());
    }

    @Operation(summary = "Buscar plato por ID", description = "Obtiene un plato específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato encontrado"),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        MenuResponseDTO plato = menuService.buscarPorId(id);
        if (plato == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Plato no encontrado con id: " + id);
        }
        return ResponseEntity.ok(plato);
    }

    @Operation(summary = "Crear plato", description = "Registra un nuevo plato en el menú")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plato creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o restaurante no existe")
    })
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

    @Operation(summary = "Actualizar plato", description = "Actualiza los datos de un plato existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato actualizado"),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado")
    })
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

    @Operation(summary = "Cambiar disponibilidad", description = "Activa o desactiva un plato sin eliminar sus datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidad actualizada"),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado")
    })
    @PatchMapping("/{id}/disponibilidad")
    public ResponseEntity<?> actualizarDisponibilidad(@PathVariable int id, @RequestParam boolean disponible) {
        MenuResponseDTO actualizado = menuService.actualizarDisponibilidad(id, disponible);
        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Plato no encontrado con id: " + id);
        }
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar plato", description = "Elimina un plato por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato eliminado"),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPlato(@PathVariable int id) {
        if (menuService.eliminarPlato(id)) {
            return ResponseEntity.ok("Plato eliminado correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Plato no encontrado con id: " + id);
    }

    // ==================== CONSULTAS DERIVADAS ====================

    @Operation(summary = "Buscar platos por restaurante", description = "Lista todos los platos de un restaurante específico")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<MenuResponseDTO>> buscarPorRestaurante(@PathVariable int restauranteId) {
        return ResponseEntity.ok(menuService.buscarPorRestaurante(restauranteId));
    }

    @Operation(summary = "Buscar platos por categoría", description = "Lista platos según su categoría")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<MenuResponseDTO>> buscarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(menuService.buscarPorCategoria(categoria));
    }

    @Operation(summary = "Listar platos disponibles", description = "Obtiene solo los platos marcados como disponibles")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/disponibles")
    public ResponseEntity<List<MenuResponseDTO>> listarDisponibles() {
        return ResponseEntity.ok(menuService.listarDisponibles());
    }

    @Operation(summary = "Menú disponible por restaurante", description = "Lista solo los platos disponibles de un restaurante específico")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/restaurante/{restauranteId}/disponibles")
    public ResponseEntity<List<MenuResponseDTO>> buscarMenuDisponiblePorRestaurante(@PathVariable int restauranteId) {
        return ResponseEntity.ok(menuService.buscarMenuDisponiblePorRestaurante(restauranteId));
    }

    @Operation(summary = "Buscar por rango de precio", description = "Lista platos cuyo precio está entre un mínimo y un máximo")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/precio")
    public ResponseEntity<List<MenuResponseDTO>> buscarPorRangoPrecio(
            @RequestParam double min,
            @RequestParam double max) {
        return ResponseEntity.ok(menuService.buscarPorRangoPrecio(min, max));
    }

    @Operation(summary = "Buscar platos por nombre", description = "Lista platos que contengan el nombre indicado")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/buscar")
    public ResponseEntity<List<MenuResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(menuService.buscarPorNombre(nombre));
    }
}