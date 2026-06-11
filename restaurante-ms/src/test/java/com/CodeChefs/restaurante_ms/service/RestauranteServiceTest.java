package com.CodeChefs.restaurante_ms.service;

import com.CodeChefs.restaurante_ms.dto.RestauranteRequestDTO;
import com.CodeChefs.restaurante_ms.dto.RestauranteResponseDTO;
import com.CodeChefs.restaurante_ms.model.Restaurante;
import com.CodeChefs.restaurante_ms.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// 📌 Activa Mockito dentro de JUnit 5
@ExtendWith(MockitoExtension.class)
class RestauranteServiceTest {

    // 📌 @Mock crea un objeto falso del Repository
    // No se conecta a MySQL, solo simula respuestas
    @Mock
    private RestauranteRepository restauranteRepository;

    // 📌 @InjectMocks crea el Service real e inyecta los mocks
    @InjectMocks
    private RestauranteService restauranteService;

    private Restaurante restaurante;
    private RestauranteRequestDTO requestDTO;

    // 📌 Se ejecuta antes de cada prueba, para inicializar datos comunes
    @BeforeEach
    void setUp() {
        restaurante = new Restaurante();
        restaurante.setId(1);
        restaurante.setNombre("Pizzeria Centro");
        restaurante.setDireccion("Av. Siempre Viva 123");
        restaurante.setTelefono("912345678");
        restaurante.setHorarioApertura("09:00");
        restaurante.setHorarioCierre("22:00");
        restaurante.setActivo(true);
        restaurante.setCalificacionPromedio(0.0);

        requestDTO = new RestauranteRequestDTO();
        requestDTO.setNombre("Pizzeria Centro");
        requestDTO.setDireccion("Av. Siempre Viva 123");
        requestDTO.setTelefono("912345678");
        requestDTO.setHorarioApertura("09:00");
        requestDTO.setHorarioCierre("22:00");
    }

    // ========================================
    // PRUEBA 1: Listar restaurantes
    // ========================================
    @Test
    @DisplayName("Debe listar todos los restaurantes")
    void debeListarTodosLosRestaurantes() {
        // 📌 Given: Dado que el repository devuelve una lista con un restaurante
        when(restauranteRepository.findAll()).thenReturn(List.of(restaurante));

        // 📌 When: Cuando ejecuto el método listarRestaurantes()
        List<RestauranteResponseDTO> resultado = restauranteService.listarRestaurantes();

        // 📌 Then: Entonces verifico que la lista tiene 1 elemento
        assertEquals(1, resultado.size());
        assertEquals("Pizzeria Centro", resultado.get(0).getNombre());

        // Verifico que el metodo findAll() se llamó exactamente una vez
        verify(restauranteRepository, times(1)).findAll();
    }

    // ========================================
    // PRUEBA 2: Buscar restaurante por ID (existente)
    // ========================================
    @Test
    @DisplayName("Debe retornar un restaurante cuando el ID existe")
    void debeRetornarRestauranteCuandoIdExiste() {
        // Given: El repository encuentra el restaurante con ID 1
        when(restauranteRepository.findById(1)).thenReturn(Optional.of(restaurante));

        // When: Busco el restaurante por ID
        RestauranteResponseDTO resultado = restauranteService.buscarPorId(1);

        // Then: Verifico que no es nulo y que los datos coinciden
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Pizzeria Centro", resultado.getNombre());

        verify(restauranteRepository, times(1)).findById(1);
    }

    // ========================================
    // PRUEBA 3: Buscar restaurante por ID (no existente)
    // ========================================
    @Test
    @DisplayName("Debe retornar null cuando el restaurante no existe")
    void debeRetornarNullCuandoIdNoExiste() {
        // Given: El repository no encuentra ningún restaurante con ID 999
        when(restauranteRepository.findById(999)).thenReturn(Optional.empty());

        // When: Busco un restaurante que no existe
        RestauranteResponseDTO resultado = restauranteService.buscarPorId(999);

        // Then: El resultado debe ser null
        assertNull(resultado);

        verify(restauranteRepository, times(1)).findById(999);
    }

    // ========================================
    // PRUEBA 4: Crear restaurante
    // ========================================
    @Test
    @DisplayName("Debe guardar un restaurante válido")
    void debeGuardarRestauranteValido() {
        // Given: Simulo que el repository guarda el restaurante y devuelve uno con ID
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);

        // When: Creo un restaurante usando el DTO
        RestauranteResponseDTO resultado = restauranteService.crearRestaurante(requestDTO);

        // Then: Verifico que el resultado no es nulo y tiene los datos correctos
        assertNotNull(resultado);
        assertEquals("Pizzeria Centro", resultado.getNombre());

        // Verifico que el metodo save() se llamó exactamente una vez
        verify(restauranteRepository, times(1)).save(any(Restaurante.class));
    }

    // ========================================
    // PRUEBA 5: Actualizar restaurante existente
    // ========================================
    @Test
    @DisplayName("Debe actualizar un restaurante existente")
    void debeActualizarRestauranteExistente() {
        // Given: El repository encuentra el restaurante
        when(restauranteRepository.findById(1)).thenReturn(Optional.of(restaurante));
        // Y también simula que guarda el restaurante actualizado
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);

        // When: Actualizo el restaurante
        RestauranteResponseDTO resultado = restauranteService.actualizarRestaurante(1, requestDTO);

        // Then: Verifico que se actualizó correctamente
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());

        verify(restauranteRepository, times(1)).findById(1);
        verify(restauranteRepository, times(1)).save(any(Restaurante.class));
    }

    // ========================================
    // PRUEBA 6: Eliminar restaurante existente
    // ========================================
    @Test
    @DisplayName("Debe eliminar un restaurante existente")
    void debeEliminarRestauranteExistente() {
        // Given: El repository confirma que el restaurante existe
        when(restauranteRepository.existsById(1)).thenReturn(true);
        // No se necesita simular deleteById porque es void

        // When: Elimino el restaurante
        boolean resultado = restauranteService.eliminarRestaurante(1);

        // Then: Debe retornar true
        assertTrue(resultado);

        verify(restauranteRepository, times(1)).existsById(1);
        verify(restauranteRepository, times(1)).deleteById(1);
    }

    // ========================================
    // PRUEBA 7: Intentar eliminar restaurante no existente
    // ========================================
    @Test
    @DisplayName("Debe retornar false al eliminar un restaurante inexistente")
    void debeRetornarFalseAlEliminarRestauranteInexistente() {
        // Given: El repository confirma que el restaurante NO existe
        when(restauranteRepository.existsById(999)).thenReturn(false);

        // When: Intento eliminar un restaurante que no existe
        boolean resultado = restauranteService.eliminarRestaurante(999);

        // Then: Debe retornar false
        assertFalse(resultado);

        verify(restauranteRepository, times(1)).existsById(999);
        // Verifico que NUNCA se intente eliminar
        verify(restauranteRepository, never()).deleteById(999);
    }
}