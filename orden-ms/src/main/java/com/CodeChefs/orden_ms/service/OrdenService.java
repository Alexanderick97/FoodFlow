package com.CodeChefs.ordenMS.service;



import com.CodeChefs.ordenMS.model.Orden;

import com.CodeChefs.ordenMS.repository.OrdenRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



import java.util.List;



@Service

public class OrdenService {


    @Autowired

    private OrdenRepository repo;



    public List<Orden> listar () {

        return repo.findAll();

    }



    public Orden guardar(Orden orden) {

        orden.setEstado("CREADA");

        return repo.save(orden);

    }



    public Orden obtener(Long id) {

        return repo.findById(id).orElse(null);

    }



    public void eliminar(Long id) {

        repo.deleteById(id);

    }

}