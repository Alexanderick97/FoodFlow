package com.CodeChefs.orden_ms.repository;



import com.CodeChefs.orden_ms.model.Orden;

import org.springframework.data.jpa.repository.JpaRepository;



public interface OrdenRepository extends JpaRepository<Orden, Long> {
}
