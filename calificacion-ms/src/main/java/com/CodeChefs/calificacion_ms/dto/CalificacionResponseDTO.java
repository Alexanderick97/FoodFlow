package com.CodeChefs.calificacion_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Datos de respuesta de una calificación")
public class CalificacionResponseDTO {
    @Schema(description = "ID único de la calificación", example = "1")
    private int id;

    @Schema(description = "ID del pedido asociado", example = "1")
    private int pedidoId;

    @Schema(description = "ID del restaurante", example = "1")
    private int restauranteId;

    @Schema(description = "ID del usuario que calificó", example = "1")
    private int usuarioId;

    @Schema(description = "Puntuación (1 a 5)", example = "5")
    private int puntuacion;

    @Schema(description = "Comentario", example = "Excelente servicio")
    private String comentario;

    @Schema(description = "Fecha de la calificación", example = "2026-06-15")
    private LocalDate fecha;

    public CalificacionResponseDTO(int id, int pedidoId, int restauranteId, int usuarioId,
                                   int puntuacion, String comentario, LocalDate fecha) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.restauranteId = restauranteId;
        this.usuarioId = usuarioId;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public int getId() { return id; }
    public int getPedidoId() { return pedidoId; }
    public int getRestauranteId() { return restauranteId; }
    public int getUsuarioId() { return usuarioId; }
    public int getPuntuacion() { return puntuacion; }
    public String getComentario() { return comentario; }
    public LocalDate getFecha() { return fecha; }
}