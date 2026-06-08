package com.CodeChefs.restaurante_ms.controller;

import com.CodeChefs.restaurante_ms.dto.RestauranteRequestDTO;
import com.CodeChefs.restaurante_ms.dto.RestauranteResponseDTO;
import com.CodeChefs.restaurante_ms.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

// @Tag agrupa los endpoints en Swagger UI bajo un nombre común.
@Tag(
        name = "Restaurante",
        description = "Endpoints para gestionar Restaurantes del sistema"
)

@RestController
@RequestMapping("/api/v1/restaurantes")
public class RestauranteController {

    private static final Logger log = LoggerFactory.getLogger(RestauranteController.class);
    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @Operation(
            summary = "Listar Restaurantes",
            description = "Obtiene todos los Restaurantes registrados en la base de datos"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado de Restaurantes obtenido correctamente"
    )
    @GetMapping
    public ResponseEntity<List<RestauranteResponseDTO>> listarRestaurantes() {
        return ResponseEntity.ok(restauranteService.listarRestaurantes());
    }

    @Operation(
            summary = "Buscar Por Id",
            description = "Busqueda de Restaurante por ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "ID de Restaurante Obtenido"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        RestauranteResponseDTO restaurante = restauranteService.buscarPorId(id);
        if (restaurante == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Restaurante no encontrado con id: " + id);
        }
        return ResponseEntity.ok(restaurante);
    }

    @Operation(
            summary = "Crear Restaurante",
            description = "Crea un nuevo de Restaurante"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Restaurante Creado"
    )
    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> crearRestaurante(@Valid @RequestBody RestauranteRequestDTO dto) {
        RestauranteResponseDTO nuevo = restauranteService.crearRestaurante(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @Operation(
            summary = "Actualizar Restaurante",
            description = "Actualiza un Restaurante anteriormente creado"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Restaurante Actualizado"
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRestaurante(@PathVariable int id, @Valid @RequestBody RestauranteRequestDTO dto) {
        RestauranteResponseDTO actualizado = restauranteService.actualizarRestaurante(id, dto);
        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Restaurante no encontrado con id: " + id);
        }
        return ResponseEntity.ok(actualizado);
    }

    @Operation(
            summary = "Eliminar Restaurante",
            description = "Elimina Restaurante por ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Restaurante Eliminado"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRestaurante(@PathVariable int id) {
        if (restauranteService.eliminarRestaurante(id)) {
            return ResponseEntity.ok("Restaurante eliminado correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Restaurante no encontrado con id: " + id);
    }

    @Operation(
            summary = "Buscar por Nombre de Restaurante",
            description = "Realiza la busqueda del restaurante por el nombre"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Restaurante Encontrado"
    )
    @GetMapping("/buscar")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(restauranteService.buscarPorNombre(nombre));
    }

    @Operation(
            summary = "Listar Restaurantes activos",
            description = "Realiza una lista de los Restaurantes activos"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Restaurantes Encontrados"
    )
    @GetMapping("/activos")
    public ResponseEntity<List<RestauranteResponseDTO>> listarActivos() {
        return ResponseEntity.ok(restauranteService.listarActivos());
    }

    @Operation(
            summary = "Buscar por Calificacion Mininima",
            description = "Realiza busqueda por calificacion minima de Restaurante"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Restaurantes Encontrados"
    )
    @GetMapping("/calificacion/{minima}")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarPorCalificacion(@PathVariable double minima) {
        return ResponseEntity.ok(restauranteService.buscarPorCalificacion(minima));
    }

    @Operation(
            summary = "Actualiza promedio",
            description = "Actualiza el promedio del restaurante"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Promedio actualizado correctamente"
    )
    @PatchMapping("/{id}/promedio")
    public ResponseEntity<?> actualizarPromedio(@PathVariable int id,
                                                @RequestParam double promedio) {
        log.info("Actualizando promedio del restaurante {} a {}", id, promedio);

        RestauranteResponseDTO restaurante = restauranteService.buscarPorId(id);
        if (restaurante == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Restaurante no encontrado con id: " + id);
        }

        RestauranteRequestDTO updateDTO = new RestauranteRequestDTO();
        updateDTO.setNombre(restaurante.getNombre());
        updateDTO.setDireccion(restaurante.getDireccion());
        updateDTO.setTelefono(restaurante.getTelefono());
        updateDTO.setHorarioApertura(restaurante.getHorarioApertura());
        updateDTO.setHorarioCierre(restaurante.getHorarioCierre());

        RestauranteResponseDTO actualizado = restauranteService.actualizarPromedio(id, promedio);
        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Restaurante no encontrado con id: " + id);
        }

        return ResponseEntity.ok("Promedio actualizado correctamente");
    }
}