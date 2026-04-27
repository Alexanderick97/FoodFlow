package com.CodeChefs.calificacion_ms.controller;

import com.CodeChefs.calificacion_ms.dto.CalificacionRequestDTO;
import com.CodeChefs.calificacion_ms.dto.CalificacionResponseDTO;
import com.CodeChefs.calificacion_ms.service.CalificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/calificaciones")
public class CalificacionController {

    private final CalificacionService calificacionService;

    public CalificacionController(CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    @GetMapping
    public ResponseEntity<List<CalificacionResponseDTO>> listarCalificaciones() {
        return ResponseEntity.ok(calificacionService.listarCalificaciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        CalificacionResponseDTO calificacion = calificacionService.buscarPorId(id);
        if (calificacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Calificación no encontrada con id: " + id);
        }
        return ResponseEntity.ok(calificacion);
    }

    @PostMapping
    public ResponseEntity<CalificacionResponseDTO> crearCalificacion(@Valid @RequestBody CalificacionRequestDTO dto) {
        CalificacionResponseDTO nueva = calificacionService.crearCalificacion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCalificacion(@PathVariable int id, @Valid @RequestBody CalificacionRequestDTO dto) {
        CalificacionResponseDTO actualizada = calificacionService.actualizarCalificacion(id, dto);
        if (actualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Calificación no encontrada con id: " + id);
        }
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCalificacion(@PathVariable int id) {
        if (calificacionService.eliminarCalificacion(id)) {
            return ResponseEntity.ok("Calificación eliminada correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Calificación no encontrada con id: " + id);
    }

    // Consultas derivadas
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<CalificacionResponseDTO>> buscarPorRestaurante(@PathVariable int restauranteId) {
        return ResponseEntity.ok(calificacionService.buscarPorRestaurante(restauranteId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CalificacionResponseDTO>> buscarPorUsuario(@PathVariable int usuarioId) {
        return ResponseEntity.ok(calificacionService.buscarPorUsuario(usuarioId));
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<CalificacionResponseDTO>> buscarPorPedido(@PathVariable int pedidoId) {
        return ResponseEntity.ok(calificacionService.buscarPorPedido(pedidoId));
    }

    @GetMapping("/restaurante/{restauranteId}/promedio")
    public ResponseEntity<?> calcularPromedioPorRestaurante(@PathVariable int restauranteId) {
        Double promedio = calificacionService.calcularPromedioPorRestaurante(restauranteId);
        if (promedio == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay calificaciones para el restaurante " + restauranteId);
        }
        return ResponseEntity.ok(new PromedioResponse(promedio));
    }

    @GetMapping("/restaurante/{restauranteId}/altas")
    public ResponseEntity<List<CalificacionResponseDTO>> buscarCalificacionesAltasPorRestaurante(
            @PathVariable int restauranteId,
            @RequestParam(defaultValue = "4") int puntuacionMinima) {
        return ResponseEntity.ok(calificacionService.buscarCalificacionesAltasPorRestaurante(restauranteId, puntuacionMinima));
    }

    // Clase auxiliar para respuesta de promedio
    static class PromedioResponse {
        private double promedio;
        public PromedioResponse(double promedio) { this.promedio = promedio; }
        public double getPromedio() { return promedio; }
    }
}