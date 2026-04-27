package com.CodeChefs.calificacion_ms.repository;

import com.CodeChefs.calificacion_ms.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Integer> {
    List<Calificacion> findByRestauranteId(int restauranteId);
    List<Calificacion> findByUsuarioId(int usuarioId);
    List<Calificacion> findByPedidoId(int pedidoId);
    List<Calificacion> findByPuntuacion(int puntuacion);

    @Query("SELECT AVG(c.puntuacion) FROM Calificacion c WHERE c.restauranteId = :restauranteId")
    Double calcularPromedioPorRestaurante(@Param("restauranteId") int restauranteId);

    List<Calificacion> findByRestauranteIdAndPuntuacionGreaterThanEqual(int restauranteId, int puntuacion);
}