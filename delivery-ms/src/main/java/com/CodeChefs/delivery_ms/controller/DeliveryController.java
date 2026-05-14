package com.deliveryms.controller;

import com.deliveryms.model.Delivery;
import com.deliveryms.service.DeliveryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

private final DeliveryService deliveryService;

public DeliveryController(DeliveryService deliveryService) {
this.deliveryService = deliveryService;
}

@GetMapping
public List<Delivery> obtenerDeliveries() {
return deliveryService.obtenerDeliveries();
}

@GetMapping("/{id}")
public Delivery obtenerDeliveryPorId(@PathVariable Long id) {

return deliveryService.obtenerDeliveryPorId(id)
.orElseThrow(() -> new RuntimeException("Delivery no encontrado"));
}

@PostMapping
public Delivery crearDelivery(@RequestBody Delivery delivery) {
return deliveryService.guardarDelivery(delivery);
}

@PutMapping("/{id}/estado")
public Delivery actualizarEstado(@PathVariable Long id,
@RequestParam String estadoEntrega) {

return deliveryService.actualizarEstado(id, estadoEntrega);
}

@DeleteMapping("/{id}")
public void eliminarDelivery(@PathVariable Long id) {
deliveryService.eliminarDelivery(id);
}
}