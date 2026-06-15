package com.CodeChefs.reserva_ms.controller;

import com.CodeChefs.reserva_ms.dto.ReservaRequestDTO;
import com.CodeChefs.reserva_ms.dto.ReservaResponseDTO;
import com.CodeChefs.reserva_ms.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "Reservas", description = "Endpoints para gestionar Reservas del sistema")
@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @Operation(summary = "Listar reservas", description = "Obtiene todas las reservas registradas")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarReservas() {
        return ResponseEntity.ok(reservaService.listarReservas());
    }

    @Operation(summary = "Buscar Reserva por ID", description = "Obtiene una reserva específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        ReservaResponseDTO reserva = reservaService.buscarPorId(id);
        if (reserva == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Reserva no encontrada con id: " + id);
        }
        return ResponseEntity.ok(reserva);
    }

    @Operation(summary = "Crear Reserva", description = "Registra una nueva Reserva en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<?> crearReserva(@Valid @RequestBody ReservaRequestDTO dto) {
        try {
            ReservaResponseDTO nueva = reservaService.crearReserva(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar Reserva", description = "Actualiza los datos de una Reserva existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva actualizada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarReserva(@PathVariable int id, @Valid @RequestBody ReservaRequestDTO dto) {
        try {
            ReservaResponseDTO actualizada = reservaService.actualizarReserva(id, dto);
            if (actualizada == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Reserva no encontrada con id: " + id);
            }
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @Operation(summary = "Cambiar Estado", description = "Actualiza el estado de una reserva (PENDIENTE, CONFIRMADA, CANCELADA, COMPLETADA)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable int id, @RequestParam String estado) {
        ReservaResponseDTO actualizada = reservaService.actualizarEstado(id, estado);
        if (actualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Reserva no encontrada con id: " + id);
        }
        return ResponseEntity.ok(actualizada);
    }

    @Operation(summary = "Eliminar Reserva", description = "Elimina una Reserva por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva eliminada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReserva(@PathVariable int id) {
        if (reservaService.eliminarReserva(id)) {
            return ResponseEntity.ok("Reserva eliminada correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Reserva no encontrada con id: " + id);
    }

    // ==================== CONSULTAS DERIVADAS ====================

    @Operation(summary = "Buscar Reservas por restaurante", description = "Lista todas las reservas de un restaurante específico")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<ReservaResponseDTO>> buscarPorRestaurante(@PathVariable int restauranteId) {
        return ResponseEntity.ok(reservaService.buscarPorRestaurante(restauranteId));
    }

    @Operation(summary = "Buscar Reservas por usuario", description = "Lista todas las reservas de un usuario específico")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReservaResponseDTO>> buscarPorUsuario(@PathVariable int usuarioId) {
        return ResponseEntity.ok(reservaService.buscarPorUsuario(usuarioId));
    }

    @Operation(summary = "Buscar Reservas por estado", description = "Lista reservas según su estado")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ReservaResponseDTO>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(reservaService.buscarPorEstado(estado));
    }

    @Operation(summary = "Reservas activas por usuario", description = "Lista las reservas activas de un usuario específico")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/usuario/{usuarioId}/activas")
    public ResponseEntity<List<ReservaResponseDTO>> buscarReservasActivasDeUsuario(@PathVariable int usuarioId) {
        return ResponseEntity.ok(reservaService.buscarReservasActivasDeUsuario(usuarioId));
    }
}