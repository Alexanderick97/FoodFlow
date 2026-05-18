package com.CodeChefs.reporte_ms.repository;

import com.CodeChefs.reporte_ms.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ReporteRepository extends JpaRepository<Reporte, Long> {

}
