package com.CodeChefs.calificacion_ms.service;

import com.CodeChefs.calificacion_ms.clients.RestauranteFeignClient;
import com.CodeChefs.calificacion_ms.dto.CalificacionRequestDTO;
import com.CodeChefs.calificacion_ms.dto.CalificacionResponseDTO;
import com.CodeChefs.calificacion_ms.model.Calificacion;
import com.CodeChefs.calificacion_ms.repository.CalificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalificacionServiceTest {

    @Mock
    private CalificacionRepository calificacionRepository;

    @Mock
    private RestauranteFeignClient restauranteFeignClient;

    @InjectMocks
    private CalificacionService calificacionService;

    private Calificacion calificacion;
    private CalificacionRequestDTO requestDTO;
    private RestauranteFeignClient.RestauranteResponse restauranteResponse;

    @BeforeEach
    void setUp() {
        calificacion = new Calificacion();
        calificacion.setId(1);
        calificacion.setPedidoId(1);
        calificacion.setRestauranteId(1);
        calificacion.setUsuarioId(1);
        calificacion.setPuntuacion(5);
        calificacion.setComentario("Excelente servicio");
        calificacion.setFecha(LocalDate.now());

        requestDTO = new CalificacionRequestDTO();
        requestDTO.setPedidoId(1);
        requestDTO.setRestauranteId(1);
        requestDTO.setUsuarioId(1);
        requestDTO.setPuntuacion(5);
        requestDTO.setComentario("Excelente servicio");

        restauranteResponse = new RestauranteFeignClient.RestauranteResponse();
        restauranteResponse.setId(1);
        restauranteResponse.setNombre("Pizzeria Centro");
        restauranteResponse.setActivo(true);
    }

    @Test
    @DisplayName("Debe listar todas las calificaciones")
    void debeListarTodasLasCalificaciones() {
        when(calificacionRepository.findAll()).thenReturn(List.of(calificacion));

        List<CalificacionResponseDTO> resultado = calificacionService.listarCalificaciones();

        assertEquals(1, resultado.size());
        verify(calificacionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar calificación cuando el ID existe")
    void debeRetornarCalificacionCuandoIdExiste() {
        when(calificacionRepository.findById(1)).thenReturn(Optional.of(calificacion));

        CalificacionResponseDTO resultado = calificacionService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(calificacionRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe retornar null cuando la calificación no existe")
    void debeRetornarNullCuandoIdNoExiste() {
        when(calificacionRepository.findById(999)).thenReturn(Optional.empty());

        CalificacionResponseDTO resultado = calificacionService.buscarPorId(999);

        assertNull(resultado);
        verify(calificacionRepository, times(1)).findById(999);
    }

    @Test
    @DisplayName("Debe crear una calificación válida")
    void debeCrearCalificacionValida() {
        when(restauranteFeignClient.getRestauranteById(1)).thenReturn(restauranteResponse);
        when(calificacionRepository.save(any(Calificacion.class))).thenReturn(calificacion);
        when(calificacionRepository.calcularPromedioPorRestaurante(1)).thenReturn(5.0);

        CalificacionResponseDTO resultado = calificacionService.crearCalificacion(requestDTO);

        assertNotNull(resultado);
        assertEquals(5, resultado.getPuntuacion());
        verify(restauranteFeignClient, times(1)).getRestauranteById(1);
        verify(calificacionRepository, times(1)).save(any(Calificacion.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el restaurante no existe")
    void debeLanzarExcepcionCuandoRestauranteNoExiste() {
        when(restauranteFeignClient.getRestauranteById(999)).thenReturn(null);

        requestDTO.setRestauranteId(999);

        assertThrows(RuntimeException.class, () -> calificacionService.crearCalificacion(requestDTO));
        verify(restauranteFeignClient, times(1)).getRestauranteById(999);
        verify(calificacionRepository, never()).save(any(Calificacion.class));
    }

    @Test
    @DisplayName("Debe eliminar una calificación existente")
    void debeEliminarCalificacionExistente() {
        when(calificacionRepository.findById(1)).thenReturn(Optional.of(calificacion));

        boolean resultado = calificacionService.eliminarCalificacion(1);

        assertTrue(resultado);
        verify(calificacionRepository, times(1)).findById(1);
        verify(calificacionRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Debe retornar false al eliminar calificación inexistente")
    void debeRetornarFalseAlEliminarCalificacionInexistente() {
        when(calificacionRepository.findById(999)).thenReturn(Optional.empty());

        boolean resultado = calificacionService.eliminarCalificacion(999);

        assertFalse(resultado);
        verify(calificacionRepository, times(1)).findById(999);
        verify(calificacionRepository, never()).deleteById(999);
    }

    @Test
    @DisplayName("Debe buscar calificaciones por restaurante")
    void debeBuscarCalificacionesPorRestaurante() {
        when(calificacionRepository.findByRestauranteId(1)).thenReturn(List.of(calificacion));

        List<CalificacionResponseDTO> resultado = calificacionService.buscarPorRestaurante(1);

        assertEquals(1, resultado.size());
        verify(calificacionRepository, times(1)).findByRestauranteId(1);
    }

    @Test
    @DisplayName("Debe buscar calificaciones por usuario")
    void debeBuscarCalificacionesPorUsuario() {
        when(calificacionRepository.findByUsuarioId(1)).thenReturn(List.of(calificacion));

        List<CalificacionResponseDTO> resultado = calificacionService.buscarPorUsuario(1);

        assertEquals(1, resultado.size());
        verify(calificacionRepository, times(1)).findByUsuarioId(1);
    }

    @Test
    @DisplayName("Debe calcular el promedio de calificaciones de un restaurante")
    void debeCalcularPromedioPorRestaurante() {
        when(calificacionRepository.calcularPromedioPorRestaurante(1)).thenReturn(4.5);

        Double resultado = calificacionService.calcularPromedioPorRestaurante(1);

        assertNotNull(resultado);
        assertEquals(4.5, resultado);
        verify(calificacionRepository, times(1)).calcularPromedioPorRestaurante(1);
    }
}