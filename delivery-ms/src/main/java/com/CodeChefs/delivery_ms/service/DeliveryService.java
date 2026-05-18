package com.CodeChefs.delivery_ms.service;

import com.CodeChefs.delivery_ms.model.Delivery;
import com.CodeChefs.delivery_ms.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

private final DeliveryRepository deliveryRepository;

public DeliveryService(DeliveryRepository deliveryRepository) {
this.deliveryRepository = deliveryRepository;
}

public List<Delivery> obtenerDeliveries() {
return deliveryRepository.findAll();
}

public Optional<Delivery> obtenerDeliveryPorId(Long id) {
return deliveryRepository.findById(id);
}

public Delivery guardarDelivery(Delivery delivery) {
return deliveryRepository.save(delivery);
}

public Delivery actualizarEstado(Long id, String estadoEntrega) {

Delivery delivery = deliveryRepository.findById(id)
.orElseThrow(() -> new RuntimeException("Delivery no encontrado"));

delivery.setEstadoEntrega(estadoEntrega);

return deliveryRepository.save(delivery);
}

public void eliminarDelivery(Long id) {
deliveryRepository.deleteById(id);
}
}
