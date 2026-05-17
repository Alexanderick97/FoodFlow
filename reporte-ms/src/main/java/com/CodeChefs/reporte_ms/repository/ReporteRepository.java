package com.CodeChefs.ReporteMS.repository;

import com.CodeChefs.ReporteMS.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ReporteRepository extends JpaRepository<Reporte, Long> {

}
