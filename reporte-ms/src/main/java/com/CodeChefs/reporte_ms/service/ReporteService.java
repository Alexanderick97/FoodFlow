package com.CodeChefs.reporte_ms.service;

import com.CodeChefs.reporte_ms.model.Reporte;
import com.CodeChefs.reporte_ms.repository.ReporteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ReporteService {

    private final ReporteRepository reporteRepository;

    public ReporteService (ReporteRepository reporteRepository){
        this.reporteRepository = reporteRepository;
    }

    public List<Reporte> obtenerReportes() {
        return reporteRepository.findAll();
    }

    public Optional<Reporte> obtenerReportePorId(Long id) {
        return reporteRepository.findById(id);
    }

    public Reporte guardarReporte(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    public void eliminarReporte(Long id) {
        reporteRepository.deleteById(id);
    }
}
