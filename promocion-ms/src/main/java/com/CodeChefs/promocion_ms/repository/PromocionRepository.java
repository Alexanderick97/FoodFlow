package com.CodeChefs.promocion_ms.repository;

import com.CodeChefs.promocion_ms.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    List<Promocion> findByActivaTrue();

    List<Promocion> findByProductoId(Long productoId);
}
