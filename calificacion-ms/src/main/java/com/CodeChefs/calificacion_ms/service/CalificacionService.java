package com.CodeChefs.calificacion_ms.service;

import com.CodeChefs.calificacion_ms.dto.CalificacionRequestDTO;
import com.CodeChefs.calificacion_ms.dto.CalificacionResponseDTO;
import com.CodeChefs.calificacion_ms.model.Calificacion;
import com.CodeChefs.calificacion_ms.repository.CalificacionRepository;
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

    public CalificacionService(CalificacionRepository calificacionRepository) {
        this.calificacionRepository = calificacionRepository;
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
        log.info("Creando nueva calificación para pedido {} y restaurante {}", dto.getPedidoId(), dto.getRestauranteId());

        Calificacion calificacion = new Calificacion();
        calificacion.setPedidoId(dto.getPedidoId());
        calificacion.setRestauranteId(dto.getRestauranteId());
        calificacion.setUsuarioId(dto.getUsuarioId());
        calificacion.setPuntuacion(dto.getPuntuacion());
        calificacion.setComentario(dto.getComentario());
        calificacion.setFecha(LocalDate.now());

        Calificacion guardado = calificacionRepository.save(calificacion);
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
        return convertirAResponseDTO(actualizado);
    }

    public boolean eliminarCalificacion(int id) {
        log.info("Eliminando calificación con id: {}", id);
        if (calificacionRepository.existsById(id)) {
            calificacionRepository.deleteById(id);
            return true;
        }
        return false;
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