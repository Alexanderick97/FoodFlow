package com.CodeChefs.PagoMS.service;

import com.CodeChefs.PagoMS.model.Pago;
import com.CodeChefs.PagoMS.repository.PagoRepository;
import org.SpringFramework.beans.factory.annotation.Autowired;
import org.SpringFramework.stereotype.Service;

import java.util.List;

@Service
public class PagoService{

    @Autowired
    private PagoRepository repo;

    public Pago procesarPago (Pago pago){
        pago.setEstado("APROBADO");
        return repo.save(pago);
    }

    public List<Pago> listar(){
        return repo.FindAll();
    }
}