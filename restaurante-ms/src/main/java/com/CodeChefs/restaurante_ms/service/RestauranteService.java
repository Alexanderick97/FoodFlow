package com.CodeChefs.restaurante_ms.service;

import com.CodeChefs.restaurante_ms.dto.RestauranteRequestDTO;
import com.CodeChefs.restaurante_ms.dto.RestauranteResponseDTO;
import com.CodeChefs.restaurante_ms.model.Restaurante;
import com.CodeChefs.restaurante_ms.repository.RestauranteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestauranteService {

    private static final Logger log = LoggerFactory.getLogger(RestauranteService.class);
    private final RestauranteRepository restauranteRepository;

    public RestauranteService(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    // Convertir entidad a ResponseDTO
    private RestauranteResponseDTO convertirAResponseDTO(Restaurante restaurante) {
        return new RestauranteResponseDTO(
                restaurante.getId(),
                restaurante.getNombre(),
                restaurante.getDireccion(),
                restaurante.getTelefono(),
                restaurante.getHorarioApertura(),
                restaurante.getHorarioCierre(),
                restaurante.isActivo(),
                restaurante.getCalificacionPromedio()
        );
    }

    public List<RestauranteResponseDTO> listarRestaurantes() {
        log.info("Listando todos los restaurantes");
        return restauranteRepository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public RestauranteResponseDTO buscarPorId(int id) {
        log.info("Buscando restaurante con id: {}", id);
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);
        return restaurante.map(this::convertirAResponseDTO).orElse(null);
    }

    public RestauranteResponseDTO crearRestaurante(RestauranteRequestDTO dto) {
        log.info("Creando nuevo restaurante: {}", dto.getNombre());

        Restaurante restaurante = new Restaurante();
        restaurante.setNombre(dto.getNombre());
        restaurante.setDireccion(dto.getDireccion());
        restaurante.setTelefono(dto.getTelefono());
        restaurante.setHorarioApertura(dto.getHorarioApertura());
        restaurante.setHorarioCierre(dto.getHorarioCierre());
        restaurante.setActivo(true);
        restaurante.setCalificacionPromedio(0.0);

        Restaurante guardado = restauranteRepository.save(restaurante);
        return convertirAResponseDTO(guardado);
    }

    public RestauranteResponseDTO actualizarRestaurante(int id, RestauranteRequestDTO dto) {
        log.info("Actualizando restaurante con id: {}", id);

        Optional<Restaurante> optional = restauranteRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }

        Restaurante restaurante = optional.get();
        restaurante.setNombre(dto.getNombre());
        restaurante.setDireccion(dto.getDireccion());
        restaurante.setTelefono(dto.getTelefono());
        restaurante.setHorarioApertura(dto.getHorarioApertura());
        restaurante.setHorarioCierre(dto.getHorarioCierre());

        Restaurante actualizado = restauranteRepository.save(restaurante);
        return convertirAResponseDTO(actualizado);
    }

    public boolean eliminarRestaurante(int id) {
        log.info("Eliminando restaurante con id: {}", id);
        if (restauranteRepository.existsById(id)) {
            restauranteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Consultas derivadas con ResponseDTO
    public List<RestauranteResponseDTO> buscarPorNombre(String nombre) {
        return restauranteRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<RestauranteResponseDTO> listarActivos() {
        return restauranteRepository.findByActivoTrue()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<RestauranteResponseDTO> buscarPorCalificacion(double calificacion) {
        return restauranteRepository.findByCalificacionPromedioGreaterThanEqual(calificacion)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
}