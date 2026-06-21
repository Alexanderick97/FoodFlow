package com.CodeChefs.pago_ms.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.CodeChefs.pago_ms.model.Pago;
import com.CodeChefs.pago_ms.repository.PagoRepository;

@ExtendWith(MockitoExtension.class)
public class PagoServiceTest {
    @Mock
    private PagoRepository repo;

    @InjectMocks
    private PagoService service;

    @Test
    void deberiaGuardarPago() {

        Pago pago = new Pago();
        pago.setMonto(15990.0);
        pago.setMetodoPago("Tarjeta");

        when(repo.save(any(Pago.class)))
                .thenAnswer(i -> i.getArgument(0));

        Pago resultado = service.guardar(pago);

        assertNotNull(resultado);
    }
    
}
