package com.CodeChefs.ordenMS.controller;



import com.CodeChefs.ordenMS.model.Orden;

import com.CodeChefs.ordenMS.service.OrdenService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.\*;



import java.util.List;



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