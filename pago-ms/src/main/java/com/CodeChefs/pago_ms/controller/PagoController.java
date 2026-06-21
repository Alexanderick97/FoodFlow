package com.CodeChefs.pago_ms.controller;

import com.CodeChefs.pago_ms.model.Pago;
import com.CodeChefs.pago_ms.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/pagos")

public class PagoController{

    @Autowired

    private PagoService service;

    @PostMapping
    public Pago pagar (@RequestBody Pago pago){
        return service.procesarPago(pago);
    }

    @GetMapping
    public List<Pago> listar(){
        return service.listar();
    }

    @GetMapping("/orden/{idOrden}")
public Pago obtenerPorOrden(
        @PathVariable Long idOrden) {

    return service.obtenerPorOrden(idOrden);
}
}
