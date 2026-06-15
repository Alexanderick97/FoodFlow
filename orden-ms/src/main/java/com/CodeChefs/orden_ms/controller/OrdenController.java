package com.CodeChefs.orden_ms.controller;



import com.CodeChefs.orden_ms.model.Orden;
import com.CodeChefs.orden_ms.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;



import java.util.List;

@Tag(
    name = "Órdenes",
    description = "Operaciones de gestión de órdenes"
)

@RestController

@RequestMapping("/ordenes")

public class OrdenController {



    @Autowired

    private OrdenService service;



    @GetMapping

    public List<Orden> listar() {

        return service.listar();

    }



    @PostMapping

    public Orden crear(@RequestBody Orden orden) {

        return service.guardar(orden);

    }



    @GetMapping ("/{id}")

    public Orden obtener(@PathVariable Long id) {

        return service.obtener(id);

    }



    @DeleteMapping ("/{id}")

    public void eliminar(@PathVariable Long id) {

        service.eliminar(id);

    }



}
