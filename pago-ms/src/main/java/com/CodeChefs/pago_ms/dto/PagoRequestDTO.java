package com.CodeChefs.pago_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Schema(description = "Datos necesarios para crear un pago")
public class PagoRequestDTO {
    @Schema(description = "Monto del pago", example = "15990")
    @Positive(message = "El monto debe ser mayor que 0")
    private Double monto;

    @Schema(description = "Método de pago", example = "Tarjeta")
    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}