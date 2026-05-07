package com.CodeChefs.PromocionMS.repository;

import com.CodeChefs.PromocionMS.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    List<Promocion> findByActivaTrue();

    List<Promocion> findByProductoId(Long productoId);
}
