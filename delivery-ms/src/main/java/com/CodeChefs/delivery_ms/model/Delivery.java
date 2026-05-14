package com.CodeChefs.deliveryMS.model;

import jakarta.persistence.*;

@Entity
@Table(name = "delivery")
public class delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private Long idOrden;
    private String nombreCliente;
    private String direccionEntrega;
    private String nombreRepartidor;
    private String estadoEntrega;

    public delivery(){

    }

    public delivery(Long id, Long idOrden, String nombreCliente, String direccionEntrega, String nombreRepartidor, String estadoEntrega){
        this.id = id;
        this.idOrden = idOrden;
        this.nombreCliente = nombreCliente;
        this.direccionEntrega = direccionEntrega;
        this.nombreRepartidor = nombreRepartidor;
        this.estadoEntrega = estadoEntrega;

    }

    public Long getId(){
        return id;
    }

    public Long getIdOrden(){
        return idOrden;
    }

    public void setIdOrden(Long idOrden){
        this.idOrden = idOrden;
    }

    public String getNombreCliente(){
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente){
        this.nombreCliente = nombreCliente;
    }

    public String getDireccionEntrega(){
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega){
        this.direccionEntrega = direccionEntrega;
    }

    public String getNombreRepartidor(){
        return nombreRepartidor;
    }

    public void setNombreRepartidor(String nombreRepartidor){
        this.nombreRepartidor = nombreRepartidor;
    }

    public String getEstadoEntrega(){
        return estadoEntrega;
    }

    public void setEstadoEntrega(String estadoEntrega){
        this.estadoEntrega = estadoEntrega;
    }

    
}