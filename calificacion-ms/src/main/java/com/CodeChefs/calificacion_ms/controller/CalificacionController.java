package com.CodeChefs.calificacion_ms.controller;

import com.CodeChefs.calificacion_ms.dto.CalificacionRequestDTO;
import com.CodeChefs.calificacion_ms.dto.CalificacionResponseDTO;
import com.CodeChefs.calificacion_ms.service.CalificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "Calificaciones", description = "Endpoints para gestionar calificaciones de restaurantes")
@RestController
@RequestMapping("/api/v1/calificaciones")
public class CalificacionController {

    private final CalificacionService calificacionService;

    public CalificacionController(CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    @Operation(summary = "Listar calificaciones", description = "Obtiene todas las calificaciones registradas")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<CalificacionResponseDTO>> listarCalificaciones() {
        return ResponseEntity.ok(calificacionService.listarCalificaciones());
    }

    @Operation(summary = "Buscar calificación por ID", description = "Obtiene una calificación específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calificación encontrada"),
            @ApiResponse(responseCode = "404", description = "Calificación no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        CalificacionResponseDTO calificacion = calificacionService.buscarPorId(id);
        if (calificacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Calificación no encontrada con id: " + id);
        }
        return ResponseEntity.ok(calificacion);
    }

    @Operation(summary = "Crear calificación", description = "Registra una nueva calificación para un restaurante (actualiza el promedio automáticamente)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Calificación creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o restaurante no existe")
    })
    @PostMapping
    public ResponseEntity<?> crearCalificacion(@Valid @RequestBody CalificacionRequestDTO dto) {
        try {
            CalificacionResponseDTO nueva = calificacionService.crearCalificacion(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar calificación", description = "Actualiza una calificación existente (recalcula el promedio del restaurante)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calificación actualizada"),
            @ApiResponse(responseCode = "404", description = "Calificación no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCalificacion(@PathVariable int id, @Valid @RequestBody CalificacionRequestDTO dto) {
        try {
            CalificacionResponseDTO actualizada = calificacionService.actualizarCalificacion(id, dto);
            if (actualizada == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Calificación no encontrada con id: " + id);
            }
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar calificación", description = "Elimina una calificación por su ID (recalcula el promedio del restaurante)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calificación eliminada"),
            @ApiResponse(responseCode = "404", description = "Calificación no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCalificacion(@PathVariable int id) {
        if (calificacionService.eliminarCalificacion(id)) {
            return ResponseEntity.ok("Calificación eliminada correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Calificación no encontrada con id: " + id);
    }

    // ==================== CONSULTAS DERIVADAS ====================

    @Operation(summary = "Buscar calificaciones por restaurante", description = "Lista todas las calificaciones de un restaurante específico")
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<CalificacionResponseDTO>> buscarPorRestaurante(@PathVariable int restauranteId) {
        return ResponseEntity.ok(calificacionService.buscarPorRestaurante(restauranteId));
    }

    @Operation(summary = "Buscar calificaciones por usuario", description = "Lista todas las calificaciones hechas por un usuario específico")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CalificacionResponseDTO>> buscarPorUsuario(@PathVariable int usuarioId) {
        return ResponseEntity.ok(calificacionService.buscarPorUsuario(usuarioId));
    }

    @Operation(summary = "Promedio de calificaciones de un restaurante", description = "Calcula el promedio de calificaciones de un restaurante")
    @GetMapping("/restaurante/{restauranteId}/promedio")
    public ResponseEntity<?> calcularPromedioPorRestaurante(@PathVariable int restauranteId) {
        Double promedio = calificacionService.calcularPromedioPorRestaurante(restauranteId);
        if (promedio == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay calificaciones para el restaurante " + restauranteId);
        }
        return ResponseEntity.ok(new PromedioResponse(promedio));
    }

    // Clase auxiliar para la respuesta del promedio
    static class PromedioResponse {
        private final double promedio;
        public PromedioResponse(double promedio) { this.promedio = promedio; }
        public double getPromedio() { return promedio; }
    }
}