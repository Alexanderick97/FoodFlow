package com.CodeChefs.reserva_ms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaRequestDTO {

    @NotNull(message = "El ID del restaurante es obligatorio")
    private int restauranteId;

    @NotNull(message = "El ID del usuario es obligatorio")
    private int usuarioId;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora es obligatoria")
    private LocalTime hora;

    @Min(value = 1, message = "El número de personas debe ser al menos 1")
    private int numeroPersonas;

    private String comentarios;

    // Getters y Setters
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