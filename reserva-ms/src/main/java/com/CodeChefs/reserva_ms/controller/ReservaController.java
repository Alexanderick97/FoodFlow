package com.CodeChefs.reserva_ms.controller;

import com.CodeChefs.reserva_ms.dto.ReservaRequestDTO;
import com.CodeChefs.reserva_ms.dto.ReservaResponseDTO;
import com.CodeChefs.reserva_ms.service.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarReservas() {
        return ResponseEntity.ok(reservaService.listarReservas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        ReservaResponseDTO reserva = reservaService.buscarPorId(id);
        if (reserva == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Reserva no encontrada con id: " + id);
        }
        return ResponseEntity.ok(reserva);
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crearReserva(@Valid @RequestBody ReservaRequestDTO dto) {
        ReservaResponseDTO nueva = reservaService.crearReserva(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarReserva(@PathVariable int id, @Valid @RequestBody ReservaRequestDTO dto) {
        ReservaResponseDTO actualizada = reservaService.actualizarReserva(id, dto);
        if (actualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Reserva no encontrada con id: " + id);
        }
        return ResponseEntity.ok(actualizada);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable int id, @RequestParam String estado) {
        ReservaResponseDTO actualizada = reservaService.actualizarEstado(id, estado);
        if (actualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Reserva no encontrada con id: " + id);
        }
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReserva(@PathVariable int id) {
        if (reservaService.eliminarReserva(id)) {
            return ResponseEntity.ok("Reserva eliminada correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Reserva no encontrada con id: " + id);
    }

    // Consultas derivadas
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<ReservaResponseDTO>> buscarPorRestaurante(@PathVariable int restauranteId) {
        return ResponseEntity.ok(reservaService.buscarPorRestaurante(restauranteId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReservaResponseDTO>> buscarPorUsuario(@PathVariable int usuarioId) {
        return ResponseEntity.ok(reservaService.buscarPorUsuario(usuarioId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ReservaResponseDTO>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(reservaService.buscarPorEstado(estado));
    }

    @GetMapping("/usuario/{usuarioId}/activas")
    public ResponseEntity<List<ReservaResponseDTO>> buscarReservasActivasDeUsuario(@PathVariable int usuarioId) {
        return ResponseEntity.ok(reservaService.buscarReservasActivasDeUsuario(usuarioId));
    }
}