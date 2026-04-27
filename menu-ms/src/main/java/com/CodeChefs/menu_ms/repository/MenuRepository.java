package com.CodeChefs.menu_ms.repository;

import com.CodeChefs.menu_ms.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    List<Menu> findByRestauranteId(int restauranteId);
    List<Menu> findByCategoria(String categoria);
    List<Menu> findByDisponibleTrue();
    List<Menu> findByRestauranteIdAndDisponibleTrue(int restauranteId);
    List<Menu> findByPrecioBetween(double min, double max);
    List<Menu> findByNombreContainingIgnoreCase(String nombre);
}