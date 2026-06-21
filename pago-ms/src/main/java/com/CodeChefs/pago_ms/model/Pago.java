package com.CodeChefs.PagoMS.model;

import jakarta.persistence.*;

@entity
public class Pago {

    @id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idOrden;
    private Double monto;
    private String metodoPago;
    private String estado;

    public Long getId(){
        return id;
    }

    public Long getIdOrden(){
        return idOrden;
    }

    public void setIdOrden(Long idOrden){
        this.idOrden = idOrden;
    }

    public Double getMonto(){
        return monto;
    }

    public void setMonto(Double monto){
        this.monto = monto
    }

    public String getMetodoPago(){
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago){
        this.metodoPago = metodoPago
    }

    public String getEstado(){
        return estado;
    }

    public void setEstado(String estado){
        this.estado = estado
    }
}
