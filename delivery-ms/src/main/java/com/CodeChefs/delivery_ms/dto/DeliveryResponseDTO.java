package com.CodeChefs.delivery_ms.dto;


public class DeliveryResponseDTO {
    private Long id;
    private Long idOrden;
    private String nombreCliente;
    private String direccionEntrega;
    private String nombreRepartidor;
    private String estadoEntrega;

    public DeliveryResponseDTO() {
    }

    public DeliveryResponseDTO(
            Long id,
            Long idOrden,
            String nombreCliente,
            String direccionEntrega,
            String nombreRepartidor,
            String estadoEntrega) {

        this.id = id;
        this.idOrden = idOrden;
        this.nombreCliente = nombreCliente;
        this.direccionEntrega = direccionEntrega;
        this.nombreRepartidor = nombreRepartidor;
        this.estadoEntrega = estadoEntrega;
    }

    public Long getId() {
        return id;
    }

    public Long getIdOrden() {
        return idOrden;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public String getNombreRepartidor() {
        return nombreRepartidor;
    }

    public String getEstadoEntrega() {
        return estadoEntrega;
    }
}

