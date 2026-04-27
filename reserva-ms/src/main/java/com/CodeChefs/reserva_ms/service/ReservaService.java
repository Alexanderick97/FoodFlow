package com.CodeChefs.reserva_ms.service;

import com.CodeChefs.reserva_ms.clients.RestauranteFeignClient;
import com.CodeChefs.reserva_ms.clients.UsuarioFeignClient;
import com.CodeChefs.reserva_ms.dto.ReservaRequestDTO;
import com.CodeChefs.reserva_ms.dto.ReservaResponseDTO;
import com.CodeChefs.reserva_ms.model.Reserva;
import com.CodeChefs.reserva_ms.repository.ReservaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private static final Logger log = LoggerFactory.getLogger(ReservaService.class);
    private final ReservaRepository reservaRepository;
    private final UsuarioFeignClient usuarioFeignClient;
    private final RestauranteFeignClient restauranteFeignClient;

    public ReservaService(ReservaRepository reservaRepository,
                          UsuarioFeignClient usuarioFeignClient,
                          RestauranteFeignClient restauranteFeignClient) {
        this.reservaRepository = reservaRepository;
        this.usuarioFeignClient = usuarioFeignClient;
        this.restauranteFeignClient = restauranteFeignClient;
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

    // Validar que el usuario existe
    private boolean usuarioExiste(int usuarioId) {
        try {
            UsuarioFeignClient.UsuarioResponse usuario = usuarioFeignClient.getUsuarioById(usuarioId);
            return usuario != null && usuario.isActivo();
        } catch (FeignException e) {
            log.error("Error al validar usuario {}: {}", usuarioId, e.getMessage());
            return false;
        }
    }

    // Validar que el restaurante existe
    private boolean restauranteExiste(int restauranteId) {
        try {
            RestauranteFeignClient.RestauranteResponse restaurante = restauranteFeignClient.getRestauranteById(restauranteId);
            return restaurante != null && restaurante.isActivo();
        } catch (FeignException e) {
            log.error("Error al validar restaurante {}: {}", restauranteId, e.getMessage());
            return false;
        }
    }

    // ============ CRUD PRINCIPAL ============

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

        // Validar que el usuario existe
        if (!usuarioExiste(dto.getUsuarioId())) {
            log.warn("Usuario {} no existe o está inactivo", dto.getUsuarioId());
            throw new RuntimeException("Usuario no existe o está inactivo");
        }

        // Validar que el restaurante existe
        if (!restauranteExiste(dto.getRestauranteId())) {
            log.warn("Restaurante {} no existe o está inactivo", dto.getRestauranteId());
            throw new RuntimeException("Restaurante no existe o está inactivo");
        }

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

    // ============ CONSULTAS DERIVADAS ============

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

    public List<ReservaResponseDTO> buscarPorFecha(LocalDate fecha) {
        return reservaRepository.findByFecha(fecha)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ReservaResponseDTO> buscarPorRestauranteYFecha(int restauranteId, LocalDate fecha) {
        return reservaRepository.findByRestauranteIdAndFecha(restauranteId, fecha)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
}