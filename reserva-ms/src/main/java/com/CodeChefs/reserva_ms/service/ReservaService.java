package com.CodeChefs.reserva_ms.service;

import com.CodeChefs.reserva_ms.dto.ReservaRequestDTO;
import com.CodeChefs.reserva_ms.dto.ReservaResponseDTO;
import com.CodeChefs.reserva_ms.model.Reserva;
import com.CodeChefs.reserva_ms.repository.ReservaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private static final Logger log = LoggerFactory.getLogger(ReservaService.class);
    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    // Convertir entidad a ResponseDTO
    private ReservaResponseDTO convertirAResponseDTO(Reserva reserva) {
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getRestauranteId(),
                reserva.getUsuarioId(),
                reserva.getFecha(),
                reserva.getHora(),
                reserva.getNumeroPersonas(),
                reserva.getEstado(),
                reserva.isActivo(),
                reserva.getComentarios()
        );
    }

    public List<ReservaResponseDTO> listarReservas() {
        log.info("Listando todas las reservas");
        return reservaRepository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public ReservaResponseDTO buscarPorId(int id) {
        log.info("Buscando reserva con id: {}", id);
        Optional<Reserva> reserva = reservaRepository.findById(id);
        return reserva.map(this::convertirAResponseDTO).orElse(null);
    }

    public ReservaResponseDTO crearReserva(ReservaRequestDTO dto) {
        log.info("Creando nueva reserva para restaurante {} y usuario {}", dto.getRestauranteId(), dto.getUsuarioId());

        Reserva reserva = new Reserva();
        reserva.setRestauranteId(dto.getRestauranteId());
        reserva.setUsuarioId(dto.getUsuarioId());
        reserva.setFecha(dto.getFecha());
        reserva.setHora(dto.getHora());
        reserva.setNumeroPersonas(dto.getNumeroPersonas());
        reserva.setComentarios(dto.getComentarios());
        reserva.setEstado("PENDIENTE");
        reserva.setActivo(true);

        Reserva guardado = reservaRepository.save(reserva);
        return convertirAResponseDTO(guardado);
    }

    public ReservaResponseDTO actualizarReserva(int id, ReservaRequestDTO dto) {
        log.info("Actualizando reserva con id: {}", id);

        Optional<Reserva> optional = reservaRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }

        Reserva reserva = optional.get();
        reserva.setRestauranteId(dto.getRestauranteId());
        reserva.setUsuarioId(dto.getUsuarioId());
        reserva.setFecha(dto.getFecha());
        reserva.setHora(dto.getHora());
        reserva.setNumeroPersonas(dto.getNumeroPersonas());
        reserva.setComentarios(dto.getComentarios());

        Reserva actualizado = reservaRepository.save(reserva);
        return convertirAResponseDTO(actualizado);
    }

    public ReservaResponseDTO actualizarEstado(int id, String estado) {
        log.info("Actualizando estado de reserva {} a {}", id, estado);

        Optional<Reserva> optional = reservaRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }

        Reserva reserva = optional.get();
        reserva.setEstado(estado);

        Reserva actualizado = reservaRepository.save(reserva);
        return convertirAResponseDTO(actualizado);
    }

    public boolean eliminarReserva(int id) {
        log.info("Eliminando reserva con id: {}", id);
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Consultas derivadas
    public List<ReservaResponseDTO> buscarPorRestaurante(int restauranteId) {
        return reservaRepository.findByRestauranteId(restauranteId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ReservaResponseDTO> buscarPorUsuario(int usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ReservaResponseDTO> buscarPorEstado(String estado) {
        return reservaRepository.findByEstado(estado)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ReservaResponseDTO> buscarReservasActivasDeUsuario(int usuarioId) {
        return reservaRepository.findByUsuarioIdAndActivoTrue(usuarioId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
}