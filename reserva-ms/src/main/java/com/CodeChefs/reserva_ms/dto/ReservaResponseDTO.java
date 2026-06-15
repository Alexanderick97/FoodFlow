package com.CodeChefs.reserva_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalTime;

@Schema(description = "Datos de respuesta de una reserva")
public class ReservaResponseDTO {

    @Schema(description = "Identificador único de la reserva", example = "1")
    private int id;

    @Schema(description = "ID del restaurante asociado", example = "1")
    private int restauranteId;

    @Schema(description = "ID del usuario que hizo la reserva", example = "1")
    private int usuarioId;

    @Schema(description = "Fecha de la reserva", example = "2026-06-20")
    private LocalDate fecha;

    @Schema(description = "Hora de la reserva", example = "20:30:00")
    private LocalTime hora;

    @Schema(description = "Número de personas", example = "4")
    private int numeroPersonas;

    @Schema(description = "Estado de la reserva", example = "PENDIENTE", allowableValues = {"PENDIENTE", "CONFIRMADA", "CANCELADA", "COMPLETADA"})
    private String estado;

    @Schema(description = "Indica si la reserva está activa", example = "true")
    private boolean activo;

    @Schema(description = "Comentarios adicionales", example = "Mesa cerca de la ventana")
    private String comentarios;

    public ReservaResponseDTO(int id, int restauranteId, int usuarioId, LocalDate fecha,
                              LocalTime hora, int numeroPersonas, String estado,
                              boolean activo, String comentarios) {
        this.id = id;
        this.restauranteId = restauranteId;
        this.usuarioId = usuarioId;
        this.fecha = fecha;
        this.hora = hora;
        this.numeroPersonas = numeroPersonas;
        this.estado = estado;
        this.activo = activo;
        this.comentarios = comentarios;
    }

    // Getters
    public int getId() { return id; }
    public int getRestauranteId() { return restauranteId; }
    public int getUsuarioId() { return usuarioId; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }
    public int getNumeroPersonas() { return numeroPersonas; }
    public String getEstado() { return estado; }
    public boolean isActivo() { return activo; }
    public String getComentarios() { return comentarios; }
}