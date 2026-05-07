package com.CodeChefs.PromocionMS.model;

import.jakarta.persistence.*;

@entity

public class Promocion{

    @id
    @GeneratedValue( strategy = GenerationType.IDENTITY)

    private Long id;
    private String descripcion;
    private Double descuento;
    private boolean activa;
    private String producto;

    public Long getId(){
        return id;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion (String descripcion){
        this.descripcion = descripcion
    }

    public Double getDescuento(){
        return descuento;
    }

    public void setDescuento (Double descuento){
        this.descuento = descuento
    }

    public boolean getActiva(){
        return activa;
    }

    public void setActiva (boolean activa){
        this.activa = true
    }

    public String getProducto(){
        return producto;
    }

    public void setProducto (String producto){
        this.producto = producto
    }
}