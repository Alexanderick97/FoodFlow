package com.CodeChefs.reserva_ms.repository;

import com.CodeChefs.reserva_ms.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByRestauranteId(int restauranteId);
    List<Reserva> findByUsuarioId(int usuarioId);
    List<Reserva> findByEstado(String estado);
    List<Reserva> findByFecha(LocalDate fecha);
    List<Reserva> findByRestauranteIdAndFecha(int restauranteId, LocalDate fecha);
    List<Reserva> findByUsuarioIdAndActivoTrue(int usuarioId);
}