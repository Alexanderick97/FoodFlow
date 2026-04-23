package com.CodeChefs.restaurante_ms.service;

import com.CodeChefs.restaurante_ms.model.Restaurante;
import com.CodeChefs.restaurante_ms.repository.RestauranteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {

    private static final Logger log = LoggerFactory.getLogger(RestauranteService.class);
    private final RestauranteRepository restauranteRepository;

    public RestauranteService(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public List<Restaurante> listarRestaurantes() {
        log.info("Listando todos los restaurantes");
        return restauranteRepository.findAll();
    }

    public Optional<Restaurante> buscarPorId(int id) {
        log.info("Buscando restaurante con id: {}", id);
        return restauranteRepository.findById(id);
    }

    public Restaurante crearRestaurante(Restaurante restaurante) {
        log.info("Creando nuevo restaurante: {}", restaurante.getNombre());
        restaurante.setActivo(true);
        return restauranteRepository.save(restaurante);
    }

    public Optional<Restaurante> actualizarRestaurante(int id, Restaurante restauranteActualizado) {
        log.info("Actualizando restaurante con id: {}", id);
        return restauranteRepository.findById(id).map(restaurante -> {
            restaurante.setNombre(restauranteActualizado.getNombre());
            restaurante.setDireccion(restauranteActualizado.getDireccion());
            restaurante.setTelefono(restauranteActualizado.getTelefono());
            restaurante.setHorarioApertura(restauranteActualizado.getHorarioApertura());
            restaurante.setHorarioCierre(restauranteActualizado.getHorarioCierre());
            restaurante.setCalificacionPromedio(restauranteActualizado.getCalificacionPromedio());
            return restauranteRepository.save(restaurante);
        });
    }

    public boolean eliminarRestaurante(int id) {
        log.info("Eliminando restaurante con id: {}", id);
        if (restauranteRepository.existsById(id)) {
            restauranteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Consultas derivadas
    public List<Restaurante> buscarPorNombre(String nombre) {
        return restauranteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Restaurante> listarActivos() {
        return restauranteRepository.findByActivoTrue();
    }

    public List<Restaurante> buscarPorCalificacion(double calificacion) {
        return restauranteRepository.findByCalificacionPromedioGreaterThanEqual(calificacion);
    }
}