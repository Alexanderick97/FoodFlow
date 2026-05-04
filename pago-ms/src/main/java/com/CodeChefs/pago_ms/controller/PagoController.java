package com.CodeChefs.PagoMS.controller;

import com.CodeChefs.PagoMS.model.Pago;
import com.CodeChefs.PagoMS.service.PagoService;
import org.SpringFramework.beans.factory.annotation.Autowired;
import org.SpringFramework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/pagos")

public class PagoController{

    @Autowired

    private PagoService service;

    @PostMapping
    public Pago Pagar (RequestBody Pago pago){
        return service.procesarPago(pago);
    }

    @GetMapping
    public List<Pago> listar(){
        return service.listar();
    }
}