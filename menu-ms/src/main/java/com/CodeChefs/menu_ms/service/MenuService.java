package com.CodeChefs.menu_ms.service;

import com.CodeChefs.menu_ms.dto.MenuRequestDTO;
import com.CodeChefs.menu_ms.dto.MenuResponseDTO;
import com.CodeChefs.menu_ms.model.Menu;
import com.CodeChefs.menu_ms.repository.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private static final Logger log = LoggerFactory.getLogger(MenuService.class);
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    // Convertir entidad a ResponseDTO
    private MenuResponseDTO convertirAResponseDTO(Menu menu) {
        return new MenuResponseDTO(
                menu.getId(),
                menu.getRestauranteId(),
                menu.getNombre(),
                menu.getDescripcion(),
                menu.getPrecio(),
                menu.getCategoria(),
                menu.isDisponible(),
                menu.getImagenUrl()
        );
    }

    public List<MenuResponseDTO> listarPlatos() {
        log.info("Listando todos los platos");
        return menuRepository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public MenuResponseDTO buscarPorId(int id) {
        log.info("Buscando plato con id: {}", id);
        Optional<Menu> menu = menuRepository.findById(id);
        return menu.map(this::convertirAResponseDTO).orElse(null);
    }

    public MenuResponseDTO crearPlato(MenuRequestDTO dto) {
        log.info("Creando nuevo plato: {} para restaurante {}", dto.getNombre(), dto.getRestauranteId());

        Menu menu = new Menu();
        menu.setRestauranteId(dto.getRestauranteId());
        menu.setNombre(dto.getNombre());
        menu.setDescripcion(dto.getDescripcion());
        menu.setPrecio(dto.getPrecio());
        menu.setCategoria(dto.getCategoria());
        menu.setImagenUrl(dto.getImagenUrl());
        menu.setDisponible(true);

        Menu guardado = menuRepository.save(menu);
        return convertirAResponseDTO(guardado);
    }

    public MenuResponseDTO actualizarPlato(int id, MenuRequestDTO dto) {
        log.info("Actualizando plato con id: {}", id);

        Optional<Menu> optional = menuRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }

        Menu menu = optional.get();
        menu.setRestauranteId(dto.getRestauranteId());
        menu.setNombre(dto.getNombre());
        menu.setDescripcion(dto.getDescripcion());
        menu.setPrecio(dto.getPrecio());
        menu.setCategoria(dto.getCategoria());
        menu.setImagenUrl(dto.getImagenUrl());

        Menu actualizado = menuRepository.save(menu);
        return convertirAResponseDTO(actualizado);
    }

    public MenuResponseDTO actualizarDisponibilidad(int id, boolean disponible) {
        log.info("Actualizando disponibilidad del plato {} a {}", id, disponible);

        Optional<Menu> optional = menuRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }

        Menu menu = optional.get();
        menu.setDisponible(disponible);

        Menu actualizado = menuRepository.save(menu);
        return convertirAResponseDTO(actualizado);
    }

    public boolean eliminarPlato(int id) {
        log.info("Eliminando plato con id: {}", id);
        if (menuRepository.existsById(id)) {
            menuRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Consultas derivadas
    public List<MenuResponseDTO> buscarPorRestaurante(int restauranteId) {
        return menuRepository.findByRestauranteId(restauranteId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MenuResponseDTO> buscarPorCategoria(String categoria) {
        return menuRepository.findByCategoria(categoria)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MenuResponseDTO> listarDisponibles() {
        return menuRepository.findByDisponibleTrue()
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MenuResponseDTO> buscarMenuDisponiblePorRestaurante(int restauranteId) {
        return menuRepository.findByRestauranteIdAndDisponibleTrue(restauranteId)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MenuResponseDTO> buscarPorRangoPrecio(double min, double max) {
        return menuRepository.findByPrecioBetween(min, max)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MenuResponseDTO> buscarPorNombre(String nombre) {
        return menuRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
}