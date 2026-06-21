package com.CodeChefs.pago_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de un pago")
public class PagoResponseDTO {
    private Long id;
    private Double monto;
    private String metodoPago;
    private String estado;

    public PagoResponseDTO() {
    }

    public PagoResponseDTO(Long id,
                           Double monto,
                           String metodoPago,
                           String estado) {

        this.id = id;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public Double getMonto() {
        return monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public String getEstado() {
        return estado;
    }
}
