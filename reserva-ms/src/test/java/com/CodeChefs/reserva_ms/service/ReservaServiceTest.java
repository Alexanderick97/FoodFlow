package com.CodeChefs.reserva_ms.service;

import com.CodeChefs.reserva_ms.clients.RestauranteFeignClient;
import com.CodeChefs.reserva_ms.clients.UsuarioFeignClient;
import com.CodeChefs.reserva_ms.dto.ReservaRequestDTO;
import com.CodeChefs.reserva_ms.dto.ReservaResponseDTO;
import com.CodeChefs.reserva_ms.model.Reserva;
import com.CodeChefs.reserva_ms.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private UsuarioFeignClient usuarioFeignClient;

    @Mock
    private RestauranteFeignClient restauranteFeignClient;

    @InjectMocks
    private ReservaService reservaService;

    private Reserva reserva;
    private ReservaRequestDTO requestDTO;
    private UsuarioFeignClient.UsuarioResponse usuarioResponse;
    private RestauranteFeignClient.RestauranteResponse restauranteResponse;

    @BeforeEach
    void setUp() {
        // Configurar entidad Reserva
        reserva = new Reserva();
        reserva.setId(1);
        reserva.setRestauranteId(1);
        reserva.setUsuarioId(1);
        reserva.setFecha(LocalDate.of(2026, 6, 20));
        reserva.setHora(LocalTime.of(20, 30));
        reserva.setNumeroPersonas(4);
        reserva.setEstado("PENDIENTE");
        reserva.setActivo(true);
        reserva.setComentarios("Mesa cerca de la ventana");

        // Configurar DTO Request
        requestDTO = new ReservaRequestDTO();
        requestDTO.setRestauranteId(1);
        requestDTO.setUsuarioId(1);
        requestDTO.setFecha(LocalDate.of(2026, 6, 20));
        requestDTO.setHora(LocalTime.of(20, 30));
        requestDTO.setNumeroPersonas(4);
        requestDTO.setComentarios("Mesa cerca de la ventana");

        // Configurar respuesta simulada del Feign Client para Usuario
        usuarioResponse = new UsuarioFeignClient.UsuarioResponse();
        usuarioResponse.setId(1);
        usuarioResponse.setNombre("Juan Perez");
        usuarioResponse.setActivo(true);

        // Configurar respuesta simulada del Feign Client para Restaurante
        restauranteResponse = new RestauranteFeignClient.RestauranteResponse();
        restauranteResponse.setId(1);
        restauranteResponse.setNombre("Pizzeria Centro");
        restauranteResponse.setActivo(true);
    }

    // ==================== LISTAR ====================

    @Test
    @DisplayName("Debe listar todas las reservas")
    void debeListarTodasLasReservas() {
        when(reservaRepository.findAll()).thenReturn(List.of(reserva));

        List<ReservaResponseDTO> resultado = reservaService.listarReservas();

        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getId());
        verify(reservaRepository, times(1)).findAll();
    }

    // ==================== BUSCAR POR ID ====================

    @Test
    @DisplayName("Debe retornar reserva cuando el ID existe")
    void debeRetornarReservaCuandoIdExiste() {
        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));

        ReservaResponseDTO resultado = reservaService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(1, resultado.getRestauranteId());
        assertEquals(1, resultado.getUsuarioId());
        verify(reservaRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe retornar null cuando la reserva no existe")
    void debeRetornarNullCuandoIdNoExiste() {
        when(reservaRepository.findById(999)).thenReturn(Optional.empty());

        ReservaResponseDTO resultado = reservaService.buscarPorId(999);

        assertNull(resultado);
        verify(reservaRepository, times(1)).findById(999);
    }

    // ==================== CREAR RESERVA ====================

    @Test
    @DisplayName("Debe crear una reserva válida (usuario y restaurante existen)")
    void debeCrearReservaValida() {
        // Simular que el usuario existe y está activo
        when(usuarioFeignClient.getUsuarioById(1)).thenReturn(usuarioResponse);
        // Simular que el restaurante existe y está activo
        when(restauranteFeignClient.getRestauranteById(1)).thenReturn(restauranteResponse);
        // Simular que la reserva se guarda
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        ReservaResponseDTO resultado = reservaService.crearReserva(requestDTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("PENDIENTE", resultado.getEstado());
        verify(usuarioFeignClient, times(1)).getUsuarioById(1);
        verify(restauranteFeignClient, times(1)).getRestauranteById(1);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el usuario no existe")
    void debeLanzarExcepcionCuandoUsuarioNoExiste() {
        // Simular que el usuario NO existe (Feign retorna null)
        when(usuarioFeignClient.getUsuarioById(999)).thenReturn(null);

        requestDTO.setUsuarioId(999);

        assertThrows(RuntimeException.class, () -> reservaService.crearReserva(requestDTO));
        verify(usuarioFeignClient, times(1)).getUsuarioById(999);
        verify(restauranteFeignClient, never()).getRestauranteById(anyInt());
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el restaurante no existe")
    void debeLanzarExcepcionCuandoRestauranteNoExiste() {
        // Simular que el usuario existe
        when(usuarioFeignClient.getUsuarioById(1)).thenReturn(usuarioResponse);
        // Simular que el restaurante NO existe
        when(restauranteFeignClient.getRestauranteById(999)).thenReturn(null);

        requestDTO.setRestauranteId(999);

        assertThrows(RuntimeException.class, () -> reservaService.crearReserva(requestDTO));
        verify(usuarioFeignClient, times(1)).getUsuarioById(1);
        verify(restauranteFeignClient, times(1)).getRestauranteById(999);
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    // ==================== ACTUALIZAR RESERVA ====================

    @Test
    @DisplayName("Debe actualizar una reserva existente")
    void debeActualizarReservaExistente() {
        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        ReservaResponseDTO resultado = reservaService.actualizarReserva(1, requestDTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(reservaRepository, times(1)).findById(1);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    @DisplayName("Debe retornar null al actualizar una reserva inexistente")
    void debeRetornarNullAlActualizarReservaInexistente() {
        when(reservaRepository.findById(999)).thenReturn(Optional.empty());

        ReservaResponseDTO resultado = reservaService.actualizarReserva(999, requestDTO);

        assertNull(resultado);
        verify(reservaRepository, times(1)).findById(999);
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    // ==================== ACTUALIZAR ESTADO ====================

    @Test
    @DisplayName("Debe actualizar el estado de una reserva existente")
    void debeActualizarEstado() {
        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        ReservaResponseDTO resultado = reservaService.actualizarEstado(1, "CONFIRMADA");

        assertNotNull(resultado);
        assertEquals("CONFIRMADA", resultado.getEstado());
        verify(reservaRepository, times(1)).findById(1);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    // ==================== ELIMINAR RESERVA ====================

    @Test
    @DisplayName("Debe eliminar una reserva existente")
    void debeEliminarReservaExistente() {
        when(reservaRepository.existsById(1)).thenReturn(true);

        boolean resultado = reservaService.eliminarReserva(1);

        assertTrue(resultado);
        verify(reservaRepository, times(1)).existsById(1);
        verify(reservaRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Debe retornar false al eliminar una reserva inexistente")
    void debeRetornarFalseAlEliminarReservaInexistente() {
        when(reservaRepository.existsById(999)).thenReturn(false);

        boolean resultado = reservaService.eliminarReserva(999);

        assertFalse(resultado);
        verify(reservaRepository, times(1)).existsById(999);
        verify(reservaRepository, never()).deleteById(999);
    }

    // ==================== CONSULTAS DERIVADAS ====================

    @Test
    @DisplayName("Debe buscar reservas por restaurante")
    void debeBuscarReservasPorRestaurante() {
        when(reservaRepository.findByRestauranteId(1)).thenReturn(List.of(reserva));

        List<ReservaResponseDTO> resultado = reservaService.buscarPorRestaurante(1);

        assertEquals(1, resultado.size());
        verify(reservaRepository, times(1)).findByRestauranteId(1);
    }

    @Test
    @DisplayName("Debe buscar reservas por usuario")
    void debeBuscarReservasPorUsuario() {
        when(reservaRepository.findByUsuarioId(1)).thenReturn(List.of(reserva));

        List<ReservaResponseDTO> resultado = reservaService.buscarPorUsuario(1);

        assertEquals(1, resultado.size());
        verify(reservaRepository, times(1)).findByUsuarioId(1);
    }

    @Test
    @DisplayName("Debe buscar reservas por estado")
    void debeBuscarReservasPorEstado() {
        when(reservaRepository.findByEstado("PENDIENTE")).thenReturn(List.of(reserva));

        List<ReservaResponseDTO> resultado = reservaService.buscarPorEstado("PENDIENTE");

        assertEquals(1, resultado.size());
        verify(reservaRepository, times(1)).findByEstado("PENDIENTE");
    }

    @Test
    @DisplayName("Debe buscar reservas activas de un usuario")
    void debeBuscarReservasActivasDeUsuario() {
        when(reservaRepository.findByUsuarioIdAndActivoTrue(1)).thenReturn(List.of(reserva));

        List<ReservaResponseDTO> resultado = reservaService.buscarReservasActivasDeUsuario(1);

        assertEquals(1, resultado.size());
        verify(reservaRepository, times(1)).findByUsuarioIdAndActivoTrue(1);
    }
}