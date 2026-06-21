package com.CodeChefs.delivery_ms.service;

import com.CodeChefs.delivery_ms.model.Delivery;
import com.CodeChefs.delivery_ms.repository.DeliveryRepository;
import org.springframework.stereotype.Service;
import com.CodeChefs.delivery_ms.client.PagoClient;
import com.CodeChefs.delivery_ms.dto.PagoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository repo;

    @Autowired
    private PagoClient pagoClient;

    public Delivery guardar(Delivery delivery) {

        PagoResponseDTO pago =
                pagoClient.obtenerPagoPorOrden(
                        delivery.getIdOrden());

        if (pago == null) {
            throw new RuntimeException(
                    "La orden aún no ha sido pagada");
        }

        if (!"PAGADO".equals(pago.getEstado())) {
            throw new RuntimeException(
                    "El pago aún no está aprobado");
        }

        delivery.setEstadoEntrega("EN_PREPARACION");

        return repo.save(delivery);
    }

    public List<Delivery> listar() {
        return repo.findAll();
    }

    public Delivery obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public Delivery actualizarEstado(Long id, String estado) {

        Delivery delivery = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Delivery no encontrado"));

        delivery.setEstadoEntrega(estado);

        return repo.save(delivery);
    }
}
