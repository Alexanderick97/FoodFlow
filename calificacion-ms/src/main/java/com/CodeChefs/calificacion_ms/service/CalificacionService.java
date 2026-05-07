package com.CodeChefs.calificacion_ms.service;

import com.CodeChefs.calificacion_ms.clients.RestauranteFeignClient;
import com.CodeChefs.calificacion_ms.dto.CalificacionRequestDTO;
import com.CodeChefs.calificacion_ms.dto.CalificacionResponseDTO;
import com.CodeChefs.calificacion_ms.model.Calificacion;
import com.CodeChefs.calificacion_ms.repository.CalificacionRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CalificacionService {

    private static final Logger log = LoggerFactory.getLogger(CalificacionService.class);
    private final CalificacionRepository calificacionRepository;
    private final RestauranteFeignClient restauranteFeignClient;

    public CalificacionService(CalificacionRepository calificacionRepository,
                               RestauranteFeignClient restauranteFeignClient) {
        this.calificacionRepository = calificacionRepository;
        this.restauranteFeignClient = restauranteFeignClient;
    }

    // Validar que el restaurante existe
    private boolean restauranteExiste(int restauranteId) {
        try {
            RestauranteFeignClient.RestauranteResponse restaurante =
                    restauranteFeignClient.getRestauranteById(restauranteId);
            return restaurante != null && restaurante.isActivo();
        } catch (FeignException e) {
            log.error("Error al validar restaurante {}: {}", restauranteId, e.getMessage());
            return false;
        }
    }

    // Actualizar el promedio del restaurante
    private void actualizarPromedioRestaurante(int restauranteId) {
        try {
            Double promedio = calificacionRepository.calcularPromedioPorRestaurante(restauranteId);
            if (promedio != null) {
                restauranteFeignClient.actualizarPromedio(restauranteId, promedio);
                log.info("Promedio actualizado para restaurante {}: {}", restauranteId, promedio);
            }
        } catch (FeignException e) {
            log.error("Error al actualizar promedio del restaurante {}: {}", restauranteId, e.getMessage());
        }
    }

    // Convertir entidad a ResponseDTO
    private CalificacionResponseDTO convertirAResponseDTO(Calificacion calificacion) {
        return new CalificacionResponseDTO(
                calificacion.getId(),
                calificacion.getPedidoId(),
                calificacion.getRestauranteId(),
                calificacion.getUsuarioId(),
                calificacion.getPuntuacion(),
                calificacion.getComentario(),
                calificacion.getFecha()
        );
    }

    public List<CalificacionResponseDTO> listarCalificaciones() {
        log.info("Listando todas las calificaciones");
        return calificacionRepository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public CalificacionResponseDTO buscarPorId(int id) {
        log.info("Buscando calificación con id: {}", id);
        Optional<Calificacion> calificacion = calificacionRepository.findById(id);
        return calificacion.map(this::convertirAResponseDTO).orElse(null);
    }

    public CalificacionResponseDTO crearCalificacion(CalificacionRequestDTO dto) {
        log.info("Creando nueva calificación para pedido {} y restaurante {}",
                dto.getPedidoId(), dto.getRestauranteId());

        // ✅ VALIDAR QUE EL RESTAURANTE EXISTE
        if (!restauranteExiste(dto.getRestauranteId())) {
            log.warn("Restaurante {} no existe o está inactivo", dto.getRestauranteId());
            throw new RuntimeException("Restaurante no existe o está inactivo");
        }

        Calificacion calificacion = new Calificacion();
        calificacion.setPedidoId(dto.getPedidoId());
        calificacion.setRestauranteId(dto.getRestauranteId());
        calificacion.setUsuarioId(dto.getUsuarioId());
        calificacion.setPuntuacion(dto.getPuntuacion());
        calificacion.setComentario(dto.getComentario());
        calificacion.setFecha(LocalDate.now());

        Calificacion guardado = calificacionRepository.save(calificacion);

        // ✅ ACTUALIZAR EL PROMEDIO DEL RESTAURANTE
        actualizarPromedioRestaurante(dto.getRestauranteId());

        return convertirAResponseDTO(guardado);
    }

    public CalificacionResponseDTO actualizarCalificacion(int id, CalificacionRequestDTO dto) {
        log.info("Actualizando calificación con id: {}", id);

        Optional<Calificacion> optional = calificacionRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }

        Calificacion calificacion = optional.get();
        calificacion.setPuntuacion(dto.getPuntuacion());
        calificacion.setComentario(dto.getComentario());

        Calificacion actualizado = calificacionRepository.save(calificacion);

        // ✅ ACTUALIZAR EL PROMEDIO DEL RESTAURANTE (puede haber cambiado)
        actualizarPromedioRestaurante(calificacion.getRestauranteId());

        return convertirAResponseDTO(actualizado);
    }

    public boolean eliminarCalificacion(int id) {
        log.info("Eliminando calificación con id: {}", id);

        // Obtener el restauranteId antes de eliminar
        Optional<Calificacion> calificacion = calificacionRepository.findById(id);
        if (calificacion.isEmpty()) {
            return false;
        }

        int restauranteId = calificacion.get().getRestauranteId();
        calificacionRepository.deleteById(id);

        // ✅ ACTUALIZAR EL PROMEDIO DEL RESTAURANTE (después de eliminar)
        actualizarPromedioRestaurante(restauranteId);

        return true;
    }

    // Consultas derivadas
    public List<CalificacionResponseDTO> buscarPorRestaurante(int restauranteId) {
        return calificacionRepository.findByRestauranteId(restauranteId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<CalificacionResponseDTO> buscarPorUsuario(int usuarioId) {
        return calificacionRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<CalificacionResponseDTO> buscarPorPedido(int pedidoId) {
        return calificacionRepository.findByPedidoId(pedidoId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public Double calcularPromedioPorRestaurante(int restauranteId) {
        log.info("Calculando promedio de calificaciones para restaurante {}", restauranteId);
        return calificacionRepository.calcularPromedioPorRestaurante(restauranteId);
    }

    public List<CalificacionResponseDTO> buscarCalificacionesAltasPorRestaurante(int restauranteId, int puntuacionMinima) {
        return calificacionRepository.findByRestauranteIdAndPuntuacionGreaterThanEqual(restauranteId, puntuacionMinima)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
}