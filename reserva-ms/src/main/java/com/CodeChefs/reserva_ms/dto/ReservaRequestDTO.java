package com.CodeChefs.reserva_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Schema(description = "Datos necesarios para crear o actualizar una reserva")
public class ReservaRequestDTO {

    @Schema(description = "ID del restaurante", example = "1", required = true)
    @NotNull(message = "El ID del restaurante es obligatorio")
    private int restauranteId;

    @Schema(description = "ID del usuario", example = "1", required = true)
    @NotNull(message = "El ID del usuario es obligatorio")
    private int usuarioId;

    @Schema(description = "Fecha de la reserva (formato YYYY-MM-DD)", example = "2026-06-20", required = true)
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @Schema(description = "Hora de la reserva (formato HH:MM:SS)", example = "20:30:00", required = true)
    @NotNull(message = "La hora es obligatoria")
    private LocalTime hora;

    @Schema(description = "Número de personas", example = "4", required = true)
    @Min(value = 1, message = "El número de personas debe ser al menos 1")
    private int numeroPersonas;

    @Schema(description = "Comentarios adicionales", example = "Mesa cerca de la ventana")
    private String comentarios;

    // Getters y Setters (mantén los que ya tienes)
    public int getRestauranteId() { return restauranteId; }
    public void setRestauranteId(int restauranteId) { this.restauranteId = restauranteId; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public int getNumeroPersonas() { return numeroPersonas; }
    public void setNumeroPersonas(int numeroPersonas) { this.numeroPersonas = numeroPersonas; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
}