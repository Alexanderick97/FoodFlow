package com.CodeChefs.orden_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Schema(description = "Datos necesarios para crear una orden")
public class OrdenRequestDTO {

    @Schema(description = "Nombre del cliente", example = "Cristofer Pizarro")
    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String cliente;

    @Schema(description = "Total de la orden", example = "15990")
    @Positive(message = "El total debe ser mayor que 0")
    private Double total;

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}