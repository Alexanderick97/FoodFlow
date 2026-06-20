package com.CodeChefs.orden_ms.service;

import com.CodeChefs.orden_ms.model.Orden;
import com.CodeChefs.orden_ms.repository.OrdenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrdenServiceTest {
    

    @Mock
    private OrdenRepository repo;

    @InjectMocks
    private OrdenService service;

    @Test
    void debeGuardarOrden() {

        Orden orden = new Orden();
        orden.setCliente("Cristofer");
        orden.setTotal(15000.0);

        when(repo.save(any(Orden.class)))
                .thenAnswer(i -> i.getArgument(0));

        Orden resultado = service.guardar(orden);

        assertNotNull(resultado);
        assertEquals("CREADA", resultado.getEstado());
        assertEquals("Cristofer", resultado.getCliente());
        assertEquals(15000.0, resultado.getTotal());
    }

    @Test
    void debeListarOrdenes() {

        Orden o1 = new Orden();
        o1.setCliente("Juan");

        Orden o2 = new Orden();
        o2.setCliente("Pedro");

        when(repo.findAll())
                .thenReturn(Arrays.asList(o1, o2));

        List<Orden> lista = service.listar();

        assertEquals(2, lista.size());
    }

    @Test
    void debeObtenerOrdenPorId() {

        Orden orden = new Orden();
        orden.setCliente("Cristofer");

        when(repo.findById(1L))
                .thenReturn(Optional.of(orden));

        Orden resultado = service.obtener(1L);

        assertNotNull(resultado);
        assertEquals("Cristofer",
                resultado.getCliente());
    }

    @Test
    void debeRetornarNullSiNoExiste() {

        when(repo.findById(1L))
                .thenReturn(Optional.empty());

        Orden resultado = service.obtener(1L);

        assertNull(resultado);
    }

    @Test
    void debeEliminarOrden() {

        doNothing().when(repo)
                .deleteById(1L);

        service.eliminar(1L);

        verify(repo, times(1))
                .deleteById(1L);
    }
}

