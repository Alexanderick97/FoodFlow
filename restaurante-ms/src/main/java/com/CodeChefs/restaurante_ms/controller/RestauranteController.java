package com.CodeChefs.restaurante_ms.controller;

import com.CodeChefs.restaurante_ms.dto.RestauranteRequestDTO;
import com.CodeChefs.restaurante_ms.dto.RestauranteResponseDTO;
import com.CodeChefs.restaurante_ms.service.RestauranteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurantes")
public class RestauranteController {

    private static final Logger log = LoggerFactory.getLogger(RestauranteController.class);
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

    // ✅ Endpoint para actualizar el promedio (usado por calificacion-ms)
    @PatchMapping("/{id}/promedio")
    public ResponseEntity<?> actualizarPromedio(@PathVariable int id,
                                                @RequestParam double promedio) {
        log.info("Actualizando promedio del restaurante {} a {}", id, promedio);

        // Buscar el restaurante usando el service (devuelve ResponseDTO)
        RestauranteResponseDTO restaurante = restauranteService.buscarPorId(id);
        if (restaurante == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Restaurante no encontrado con id: " + id);
        }

        // Crear un RequestDTO para actualizar solo el promedio
        RestauranteRequestDTO updateDTO = new RestauranteRequestDTO();
        updateDTO.setNombre(restaurante.getNombre());
        updateDTO.setDireccion(restaurante.getDireccion());
        updateDTO.setTelefono(restaurante.getTelefono());
        updateDTO.setHorarioApertura(restaurante.getHorarioApertura());
        updateDTO.setHorarioCierre(restaurante.getHorarioCierre());
        // La calificacionPromedio no está en el DTO, se maneja aparte

        // Actualizar usando el service
        RestauranteResponseDTO actualizado = restauranteService.actualizarPromedio(id, promedio);
        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Restaurante no encontrado con id: " + id);
        }

        return ResponseEntity.ok("Promedio actualizado correctamente");
    }
}