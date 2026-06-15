package com.CodeChefs.menu_ms.service;

import com.CodeChefs.menu_ms.clients.RestauranteFeignClient;
import com.CodeChefs.menu_ms.dto.MenuRequestDTO;
import com.CodeChefs.menu_ms.dto.MenuResponseDTO;
import com.CodeChefs.menu_ms.model.Menu;
import com.CodeChefs.menu_ms.repository.MenuRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private RestauranteFeignClient restauranteFeignClient;  // ← AGREGAR ESTO

    @InjectMocks
    private MenuService menuService;

    private Menu menu;
    private MenuRequestDTO requestDTO;
    private RestauranteFeignClient.RestauranteResponse restauranteResponse;

    @BeforeEach
    void setUp() {
        menu = new Menu();
        menu.setId(1);
        menu.setRestauranteId(1);
        menu.setNombre("Pizza Margarita");
        menu.setDescripcion("Tomate, mozzarella, albahaca");
        menu.setPrecio(8990);
        menu.setCategoria("PRINCIPAL");
        menu.setDisponible(true);

        requestDTO = new MenuRequestDTO();
        requestDTO.setRestauranteId(1);
        requestDTO.setNombre("Pizza Margarita");
        requestDTO.setDescripcion("Tomate, mozzarella, albahaca");
        requestDTO.setPrecio(8990);
        requestDTO.setCategoria("PRINCIPAL");

        // Simular la respuesta del Feign Client
        restauranteResponse = new RestauranteFeignClient.RestauranteResponse();
        restauranteResponse.setId(1);
        restauranteResponse.setNombre("Pizzeria Centro");
        restauranteResponse.setActivo(true);
    }

    // ============ PRUEBAS DE LISTAR ============

    @Test
    @DisplayName("Debe listar todos los platos")
    void debeListarTodosLosPlatos() {
        when(menuRepository.findAll()).thenReturn(List.of(menu));

        List<MenuResponseDTO> resultado = menuService.listarPlatos();

        assertEquals(1, resultado.size());
        assertEquals("Pizza Margarita", resultado.get(0).getNombre());
        verify(menuRepository, times(1)).findAll();
    }

    // ============ PRUEBAS DE BUSCAR POR ID ============

    @Test
    @DisplayName("Debe retornar plato cuando el ID existe")
    void debeRetornarPlatoCuandoIdExiste() {
        when(menuRepository.findById(1)).thenReturn(Optional.of(menu));

        MenuResponseDTO resultado = menuService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Pizza Margarita", resultado.getNombre());
        verify(menuRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe retornar null cuando el plato no existe")
    void debeRetornarNullCuandoIdNoExiste() {
        when(menuRepository.findById(999)).thenReturn(Optional.empty());

        MenuResponseDTO resultado = menuService.buscarPorId(999);

        assertNull(resultado);
        verify(menuRepository, times(1)).findById(999);
    }

    // ============ PRUEBAS DE CREAR ============

    @Test
    @DisplayName("Debe guardar un plato válido")
    void debeGuardarPlatoValido() {
        // Simular que el restaurante existe (Feign Client)
        when(restauranteFeignClient.getRestauranteById(1)).thenReturn(restauranteResponse);
        when(menuRepository.save(any(Menu.class))).thenReturn(menu);

        MenuResponseDTO resultado = menuService.crearPlato(requestDTO);

        assertNotNull(resultado);
        assertEquals("Pizza Margarita", resultado.getNombre());
        verify(restauranteFeignClient, times(1)).getRestauranteById(1);
        verify(menuRepository, times(1)).save(any(Menu.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el restaurante no existe")
    void debeLanzarExcepcionCuandoRestauranteNoExiste() {
        // Simular que el restaurante NO existe (Feign Client lanza excepción o retorna null)
        when(restauranteFeignClient.getRestauranteById(999)).thenThrow(new RuntimeException("Restaurante no existe"));

        requestDTO.setRestauranteId(999);

        assertThrows(RuntimeException.class, () -> menuService.crearPlato(requestDTO));
        verify(restauranteFeignClient, times(1)).getRestauranteById(999);
        verify(menuRepository, never()).save(any(Menu.class));
    }

    // ============ PRUEBAS DE ACTUALIZAR ============

    @Test
    @DisplayName("Debe actualizar un plato existente")
    void debeActualizarPlatoExistente() {
        when(restauranteFeignClient.getRestauranteById(1)).thenReturn(restauranteResponse);
        when(menuRepository.findById(1)).thenReturn(Optional.of(menu));
        when(menuRepository.save(any(Menu.class))).thenReturn(menu);

        MenuResponseDTO resultado = menuService.actualizarPlato(1, requestDTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(restauranteFeignClient, times(1)).getRestauranteById(1);
        verify(menuRepository, times(1)).findById(1);
        verify(menuRepository, times(1)).save(any(Menu.class));
    }

    // ============ PRUEBAS DE ELIMINAR ============

    @Test
    @DisplayName("Debe eliminar un plato existente")
    void debeEliminarPlatoExistente() {
        when(menuRepository.existsById(1)).thenReturn(true);

        boolean resultado = menuService.eliminarPlato(1);

        assertTrue(resultado);
        verify(menuRepository, times(1)).existsById(1);
        verify(menuRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Debe retornar false al eliminar plato inexistente")
    void debeRetornarFalseAlEliminarPlatoInexistente() {
        when(menuRepository.existsById(999)).thenReturn(false);

        boolean resultado = menuService.eliminarPlato(999);

        assertFalse(resultado);
        verify(menuRepository, times(1)).existsById(999);
        verify(menuRepository, never()).deleteById(999);
    }

    // ============ PRUEBAS DE ACTUALIZAR DISPONIBILIDAD ============

    @Test
    @DisplayName("Debe actualizar disponibilidad de un plato existente")
    void debeActualizarDisponibilidad() {
        when(menuRepository.findById(1)).thenReturn(Optional.of(menu));
        when(menuRepository.save(any(Menu.class))).thenReturn(menu);

        MenuResponseDTO resultado = menuService.actualizarDisponibilidad(1, false);

        assertNotNull(resultado);
        assertFalse(resultado.isDisponible());
        verify(menuRepository, times(1)).findById(1);
        verify(menuRepository, times(1)).save(any(Menu.class));
    }

    // ============ PRUEBAS DE CONSULTAS DERIVADAS ============

    @Test
    @DisplayName("Debe buscar platos por restaurante")
    void debeBuscarPlatosPorRestaurante() {
        when(menuRepository.findByRestauranteId(1)).thenReturn(List.of(menu));

        List<MenuResponseDTO> resultado = menuService.buscarPorRestaurante(1);

        assertEquals(1, resultado.size());
        verify(menuRepository, times(1)).findByRestauranteId(1);
    }

    @Test
    @DisplayName("Debe buscar platos por categoría")
    void debeBuscarPlatosPorCategoria() {
        when(menuRepository.findByCategoria("PRINCIPAL")).thenReturn(List.of(menu));

        List<MenuResponseDTO> resultado = menuService.buscarPorCategoria("PRINCIPAL");

        assertEquals(1, resultado.size());
        verify(menuRepository, times(1)).findByCategoria("PRINCIPAL");
    }

    @Test
    @DisplayName("Debe listar platos disponibles")
    void debeListarPlatosDisponibles() {
        when(menuRepository.findByDisponibleTrue()).thenReturn(List.of(menu));

        List<MenuResponseDTO> resultado = menuService.listarDisponibles();

        assertEquals(1, resultado.size());
        verify(menuRepository, times(1)).findByDisponibleTrue();
    }

    @Test
    @DisplayName("Debe buscar menú disponible por restaurante")
    void debeBuscarMenuDisponiblePorRestaurante() {
        when(menuRepository.findByRestauranteIdAndDisponibleTrue(1)).thenReturn(List.of(menu));

        List<MenuResponseDTO> resultado = menuService.buscarMenuDisponiblePorRestaurante(1);

        assertEquals(1, resultado.size());
        verify(menuRepository, times(1)).findByRestauranteIdAndDisponibleTrue(1);
    }

    @Test
    @DisplayName("Debe buscar platos por rango de precio")
    void debeBuscarPorRangoPrecio() {
        when(menuRepository.findByPrecioBetween(5000, 10000)).thenReturn(List.of(menu));

        List<MenuResponseDTO> resultado = menuService.buscarPorRangoPrecio(5000, 10000);

        assertEquals(1, resultado.size());
        verify(menuRepository, times(1)).findByPrecioBetween(5000, 10000);
    }

    @Test
    @DisplayName("Debe buscar platos por nombre")
    void debeBuscarPorNombre() {
        when(menuRepository.findByNombreContainingIgnoreCase("Pizza")).thenReturn(List.of(menu));

        List<MenuResponseDTO> resultado = menuService.buscarPorNombre("Pizza");

        assertEquals(1, resultado.size());
        verify(menuRepository, times(1)).findByNombreContainingIgnoreCase("Pizza");
    }
}