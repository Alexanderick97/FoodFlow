package com.CodeChefs.promocion_ms.model;

import jakarta.persistence.*;

@Entity

public class Promocion{

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)

    private Long id;
    private String nombre;
    private String descripcion;
    private Double descuento;
    private boolean activa;
    private String productoId;

    public Long getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre (String nombre){
        this.nombre = nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion (String descripcion){
        this.descripcion = descripcion;
    }

    public Double getDescuento(){
        return descuento;
    }

    public void setDescuento (Double descuento){
        this.descuento = descuento;
    }

    public boolean getActiva(){
        return activa;
    }

    public void setActiva (boolean activa){
        this.activa = true;
    }

    public String getProductoId(){
        return productoId;
    }

    public void setProductoId (String productoId){
        this.productoId = productoId;
    }
}
