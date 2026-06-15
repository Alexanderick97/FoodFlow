package com.CodeChefs.calificacion_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos necesarios para crear o actualizar una calificación")
public class CalificacionRequestDTO {

    @Schema(description = "ID del pedido asociado", example = "1", required = true)
    @NotNull(message = "El ID del pedido es obligatorio")
    private int pedidoId;

    @Schema(description = "ID del restaurante calificado", example = "1", required = true)
    @NotNull(message = "El ID del restaurante es obligatorio")
    private int restauranteId;

    @Schema(description = "ID del usuario que califica", example = "1", required = true)
    @NotNull(message = "El ID del usuario es obligatorio")
    private int usuarioId;

    @Schema(description = "Puntuación (1 a 5 estrellas)", example = "5", required = true, minimum = "1", maximum = "5")
    @NotNull(message = "La puntuación es obligatoria")
    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 5, message = "La puntuación máxima es 5")
    private int puntuacion;

    @Schema(description = "Comentario de la calificación", example = "Excelente servicio")
    @Size(max = 500, message = "El comentario no puede tener más de 500 caracteres")
    private String comentario;

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