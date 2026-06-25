package com.CodeChefs.delivery_ms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.CodeChefs.delivery_ms.model.Delivery;
import com.CodeChefs.delivery_ms.repository.DeliveryRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeliveryServiceTest {
    @Mock
    private DeliveryRepository repo;

    @InjectMocks
    private DeliveryService service;

    @Test
    void deberiaGuardarDelivery() {

        Delivery delivery = new Delivery();

        delivery.setIdOrden(1L);
        delivery.setNombreCliente("Cristofer");
        delivery.setDireccionEntrega("Valparaiso");

        when(repo.save(any(Delivery.class)))
                .thenAnswer(i -> i.getArgument(0));

        Delivery resultado = service.guardar(delivery);

        assertNotNull(resultado);
    }

    @Test
void actualizarEstadoDelivery() {

    Delivery delivery = new Delivery();
    delivery.setEstadoEntrega("PENDIENTE");

    when(repo.findById(1L))
            .thenReturn(Optional.of(delivery));

    when(repo.save(any(Delivery.class)))
            .thenReturn(delivery);

    Delivery resultado =
            service.actualizarEstado(
                    1L,
                    "ENTREGADO");

    assertEquals("ENTREGADO",
            resultado.getEstadoEntrega());
}
}

