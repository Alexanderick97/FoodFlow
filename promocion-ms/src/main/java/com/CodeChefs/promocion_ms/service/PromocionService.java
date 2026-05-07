package com.CodeChefs.PromocionMS.service;

import com.CodeChefs.PromocionMS.model.Promocion;
import com.CodeChefs.PromocionMS.repository.PromocionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;

    public List<Promocion> obtenerTodas() {
        return promocionRepository.findAll();
    }

    public Optional<Promocion> obtenerPorId(Long id) {
        return promocionRepository.findById(id);
    }
    public Promocion guardarPromocion(Promocion promocion) {
        return promocionRepository.save(promocion);
    }

    public Promocion actualizarPromocion(Long id, Promocion nuevaPromocion) {
        return promocionRepository.findById(id).map(promocion -> {
            promocion.setNombre(nuevaPromocion.getNombre());
            promocion.setDescripcion(nuevaPromocion.getDescripcion());
            promocion.setDescuento(nuevaPromocion.getDescuento());
            promocion.setActiva(nuevaPromocion.getActiva());
            promocion.setProductoId(nuevaPromocion.getProductoId());
            return promocionRepository.save(promocion);
        }).orElseThrow(() -> new RuntimeException("Promoción no encontrada con ID: " + id));
    }

    public void eliminarPromocion(Long id) {
        promocionRepository.deleteById(id);
    }

    public List<Promocion> obtenerPromocionesActivas() {
        return promocionRepository.findByActivaTrue();
    }

    public List<Promocion> obtenerPorProducto(Long productoId) {
        return promocionRepository.findByProductoId(productoId);
    }
}