package com.CodeChefs.calificacion_ms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CalificacionRequestDTO {

    @NotNull(message = "El ID del pedido es obligatorio")
    private int pedidoId;

    @NotNull(message = "El ID del restaurante es obligatorio")
    private int restauranteId;

    @NotNull(message = "El ID del usuario es obligatorio")
    private int usuarioId;

    @NotNull(message = "La puntuación es obligatoria")
    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 5, message = "La puntuación máxima es 5")
    private int puntuacion;

    @Size(max = 500, message = "El comentario no puede tener más de 500 caracteres")
    private String comentario;

    // Getters y Setters
    public int getPedidoId() { return pedidoId; }
    public void setPedidoId(int pedidoId) { this.pedidoId = pedidoId; }

    public int getRestauranteId() { return restauranteId; }
    public void setRestauranteId(int restauranteId) { this.restauranteId = restauranteId; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getPuntuacion() { return puntuacion; }
    public void setPuntuacion(int puntuacion) { this.puntuacion = puntuacion; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}