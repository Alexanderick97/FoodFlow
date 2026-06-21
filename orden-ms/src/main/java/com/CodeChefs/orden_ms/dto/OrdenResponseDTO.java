package com.CodeChefs.orden_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de respuesta de una orden")
public class OrdenResponseDTO {

    private Long id;
    private String cliente;
    private Double total;
    private String estado;

    public OrdenResponseDTO() {
    }

    public OrdenResponseDTO(Long id, String cliente,
                            Double total, String estado) {
        this.id = id;
        this.cliente = cliente;
        this.total = total;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public Double getTotal() {
        return total;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @FeignClient(name = "orden-ms", url = "http://localhost:8086")
public interface OrdenClient {

    @GetMapping("/ordenes/{id}")
    OrdenResponseDTO obtenerOrden(@PathVariable Long id);

}
}
