package com.CodeChefs.reporte_ms.controller;

import com.CodeChefs.reporte_ms.model.Reporte;
import com.CodeChefs.reporte_ms.service.ReporteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    public List<Reporte> obtenerReportes() {
        return reporteService.obtenerReportes();
    }

    @GetMapping("/{id}")
    public Reporte obtenerReportePorId(@PathVariable Long id) {
        return reporteService.obtenerReportePorId(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
    }

    @PostMapping
    public Reporte crearReporte(@RequestBody Reporte reporte) {
        return reporteService.guardarReporte(reporte);
    }

    @DeleteMapping("/{id}")
    public void eliminarReporte(@PathVariable Long id) {
        reporteService.eliminarReporte(id);
    }
}
